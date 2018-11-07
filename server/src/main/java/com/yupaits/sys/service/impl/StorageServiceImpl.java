package com.yupaits.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yupaits.auth.vo.UserVO;
import com.yupaits.commons.consts.AppConsts;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.EncryptUtils;
import com.yupaits.sys.dto.ImageProps;
import com.yupaits.sys.entity.StoreFile;
import com.yupaits.sys.service.IStoreFileService;
import com.yupaits.sys.service.StorageService;
import com.yupaits.web.prop.UploadProps;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yupaits
 * @date 2018/11/7
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    private final UploadProps uploadProps;
    private final FastFileStorageClient storageClient;
    private final IStoreFileService storeFileService;

    @Autowired
    public StorageServiceImpl(UploadProps uploadProps, FastFileStorageClient storageClient, IStoreFileService storeFileService) {
        this.uploadProps = uploadProps;
        this.storageClient = storageClient;
        this.storeFileService = storeFileService;
    }

    @Override
    public Result store(MultipartFile file, boolean isPrivacy) {
        try {
            StoreFile storeFile = storeFile(isPrivacy, file);
            if (storeFileService.save(storeFile)) {
                Long fileId = storeFileService.getOne(new QueryWrapper<StoreFile>()
                        .eq("group_name", storeFile.getGroupName())
                        .eq("path", storeFile.getPath())).getId();
                return ResultWrapper.success(fileId.toString());
            }
            return ResultWrapper.fail(ResultCode.CREATE_FAIL);
        } catch (IOException e) {
            log.error("保存文件出错, 文件名: {}", file.getOriginalFilename(), e);
            return ResultWrapper.fail(ResultCode.FILE_UPLOAD_FAIL);
        }
    }

    @Override
    public Result batchStore(List<MultipartFile> files, boolean isPrivacy) {
        List<Long> fileIds = Lists.newArrayList();
        for (MultipartFile file : files) {
            try {
                StoreFile storeFile = storeFile(isPrivacy, file);
                if (storeFileService.save(storeFile)) {
                    Long fileId = storeFileService.getOne(new QueryWrapper<StoreFile>()
                            .eq("group_name", storeFile.getGroupName())
                            .eq("path", storeFile.getPath())).getId();
                    fileIds.add(fileId);
                } else {
                    storeFileService.removeByIds(fileIds);
                    return ResultWrapper.fail(ResultCode.CREATE_FAIL);
                }
            } catch (IOException e) {
                log.error("保存文件出错, 文件名: {}", file.getOriginalFilename(), e);
                storeFileService.removeByIds(fileIds);
                return ResultWrapper.fail(ResultCode.FILE_UPLOAD_FAIL);
            }
        }
        return ResultWrapper.success(fileIds.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    @Override
    public Result storeImage(MultipartFile image, int thumbnailWidth, int thumbnailHeight, float quality,
                             boolean isPrivacy, boolean saveOrigin, boolean saveWithThumbnail, boolean saveWithWatermark) {
        ImageProps props = new ImageProps(thumbnailWidth, thumbnailHeight, quality, isPrivacy, saveOrigin,
                saveWithThumbnail, saveWithWatermark);
        return storeImage(image, props);
    }

    @Override
    public Result storeImage(MultipartFile image, ImageProps props) {
        if (!props.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        String imageName = image.getOriginalFilename();
        Map<String, Object> resultData = Maps.newHashMap();
        List<Long> savedFileIds = Lists.newArrayList();
        if (props.isSaveOrigin()) {
            try {
                StoreFile storeFile = storeFile(props.isPrivacy(), image);
                if (storeFileService.save(storeFile)) {
                    Long fileId = storeFileService.getOne(new QueryWrapper<StoreFile>()
                            .eq("group_name", storeFile.getGroupName())
                            .eq("path", storeFile.getPath())).getId();
                    resultData.put(AppConsts.UploadConsts.ORIGIN_IMAGE, fileId.toString());
                    savedFileIds.add(fileId);
                }
            } catch (IOException e) {
                log.error("保存原图出错, 图片名: {}, 图片参数: {}", imageName, props, e);
                return ResultWrapper.fail(ResultCode.FILE_UPLOAD_FAIL);
            }
        }
        if (!props.isSaveWithThumbnail() && !props.isSaveWithWatermark()) {
            return ResultWrapper.success(resultData);
        }
        try {
            Thumbnails.Builder thumbnailBuilder = Thumbnails.of(image.getInputStream());
            Watermark watermark;
            File tempFile = new File(genTempFilePath(uploadProps.getTempDir(), image));
            FileUtils.forceMkdir(tempFile.getParentFile());
            if (props.isSaveWithThumbnail()) {
                if (props.isSaveWithWatermark()) {
                    watermark = new Watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(uploadProps.getWatermark())), 1.0f);
                    thumbnailBuilder.size(props.getThumbnailWidth(), props.getThumbnailHeight())
                            .outputQuality(props.getQuality())
                            .watermark(watermark)
                            .toFile(tempFile);
                    StoreFile storeFile = storeImage(props.isPrivacy(), tempFile);
                    if (storeFileService.save(storeFile)) {
                        Long fileId = storeFileService.getOne(new QueryWrapper<StoreFile>()
                                .eq("group_name", storeFile.getGroupName())
                                .eq("path", storeFile.getPath())).getId();
                        resultData.put(AppConsts.UploadConsts.THUMBNAIL_WATERMARK_IMAGE, fileId.toString());
                        savedFileIds.add(fileId);
                    }
                } else {
                    thumbnailBuilder.size(props.getThumbnailWidth(), props.getThumbnailHeight())
                            .outputQuality(props.getQuality())
                            .toFile(tempFile);
                    StoreFile storeFile = storeImage(props.isPrivacy(), tempFile);
                    if (storeFileService.save(storeFile)) {
                        Long fileId = storeFileService.getOne(new QueryWrapper<StoreFile>()
                                .eq("group_name", storeFile.getGroupName())
                                .eq("path", storeFile.getPath())).getId();
                        resultData.put(AppConsts.UploadConsts.THUMBNAIL_IMAGE, fileId.toString());
                        savedFileIds.add(fileId);
                    }
                }
            } else if (props.isSaveWithWatermark()) {
                watermark = new Watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(uploadProps.getWatermark())), 1.0f);
                thumbnailBuilder.scale(1.0).watermark(watermark).toFile(tempFile);
                StoreFile storeFile = storeImage(props.isPrivacy(), tempFile);
                if (storeFileService.save(storeFile)) {
                    Long fileId = storeFileService.getOne(new QueryWrapper<StoreFile>()
                            .eq("group_name", storeFile.getGroupName())
                            .eq("path", storeFile.getPath())).getId();
                    resultData.put(AppConsts.UploadConsts.WATERMARK_IMAGE, fileId.toString());
                    savedFileIds.add(fileId);
                }
            }
            FileUtils.forceDelete(tempFile);
        } catch (IOException e) {
            log.error("保存缩略图/水印图出错, 图片名: {}, 图片参数: {}", imageName, props, e);
            storeFileService.removeByIds(savedFileIds);
            return ResultWrapper.fail(ResultCode.FILE_UPLOAD_FAIL);
        }
        return ResultWrapper.success(resultData);

    }

    /**
     * 保存文件
     */
    private StoreFile storeFile(boolean isPrivacy, MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();
        StorePath filePath = storageClient.uploadFile(fileInputStream, file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        IOUtils.closeQuietly(fileInputStream);
        return buildStoreFile(isPrivacy, filePath, file.getOriginalFilename());
    }

    /**
     * 保存图片
     */
    private StoreFile storeImage(boolean isPrivacy, File file) throws IOException {
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        StorePath filePath = storageClient.uploadFile(fileInputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
        IOUtils.closeQuietly(fileInputStream);
        return buildStoreFile(isPrivacy, filePath, file.getName());
    }

    /**
     * 生成临时文件路径
     */
    private String genTempFilePath(String tempDir, MultipartFile file) {
        StringBuilder builder = new StringBuilder(tempDir);
        String filename = file.getOriginalFilename();
        LocalDateTime now = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        return builder.append(FilenameUtils.getBaseName(filename))
                .append(now.format(DateTimeFormatter.ofPattern(AppConsts.FILENAME_DATETIME_PATTERN)))
                .append(FilenameUtils.EXTENSION_SEPARATOR_STR)
                .append(FilenameUtils.getExtension(filename))
                .toString();
    }

    /**
     * 根据StorePath构建StoreFile
     */
    private StoreFile buildStoreFile(boolean isPrivacy, StorePath storePath, String filename) {
        return new StoreFile().setGroupName(storePath.getGroup())
                .setPath(storePath.getPath())
                .setFilename(filename)
                .setPrivacy(isPrivacy)
                .setPrivacyCode(isPrivacy ?
                        EncryptUtils.genPrivacyCode(((UserVO) SecurityUtils.getSubject().getPrincipal()).getUsername())
                        : null);
    }
}
