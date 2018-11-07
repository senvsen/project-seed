package com.yupaits.sys.controller;

import com.yupaits.commons.annotation.NoCache;
import com.yupaits.commons.result.Result;
import com.yupaits.sys.dto.ImageProps;
import com.yupaits.sys.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件上传下载控制器
 * @author yupaits
 * @date 2018/11/7
 */
@NoCache
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final StorageService storageService;

    @Autowired
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @ApiOperation("上传文件")
    @ApiImplicitParam(name = "isPrivacy", value = "是否保密", dataType = "Boolean", paramType = "query")
    @PostMapping("/file")
    public Result uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(name = "isPrivacy", required = false, defaultValue = "false") boolean isPrivacy) {
        return storageService.store(file, isPrivacy);
    }

    @ApiOperation("批量上传文件")
    @PostMapping("/file/batch")
    public Result batchUploadFile(HttpServletRequest request,
                                  @RequestParam(name = "isPrivacy", required = false, defaultValue = "false") boolean isPrivacy) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        return storageService.batchStore(files, isPrivacy);
    }

    @ApiOperation("上传图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "thumbnailWidth", value = "缩略图宽度", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "thumbnailHeight", value = "缩略图高度", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "quality", value = "图片质量（压缩比，如0.8表示压缩比为80%）", dataType = "Float", paramType = "query"),
            @ApiImplicitParam(name = "isPrivacy", value = "是否保密", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "saveOrigin", value = "是否保存原图", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "saveWithThumbnail", value = "是否保存缩略图", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "saveWithWatermark", value = "是否保存水印图", dataType = "Boolean", paramType = "query")})
    @PostMapping("/image")
    public Result uploadImage(@RequestParam("file") MultipartFile image,
                              @RequestParam(name = "thumbnailWidth", required = false, defaultValue = "100") int thumbnailWidth,
                              @RequestParam(name = "thumbnailHeight", required = false, defaultValue = "100") int thumbnailHeight,
                              @RequestParam(name = "quality", required = false, defaultValue = "0.8") float quality,
                              @RequestParam(name = "isPrivacy", required = false, defaultValue = "false") boolean isPrivacy,
                              @RequestParam(name = "saveOrigin", required = false, defaultValue = "true") boolean saveOrigin,
                              @RequestParam(name = "saveWithThumbnail", required = false, defaultValue = "false") boolean saveWithThumbnail,
                              @RequestParam(name = "saveWithWatermark", required = false, defaultValue = "false") boolean saveWithWatermark) {
        return storageService.storeImage(image, thumbnailWidth, thumbnailHeight, quality, isPrivacy, saveOrigin,
                saveWithThumbnail, saveWithWatermark);
    }

    @ApiOperation("异步上传图片")
    @PostMapping("/image/async")
    public Result asyncUploadImage(@RequestPart("file") MultipartFile image, @RequestPart ImageProps props) {
        return storageService.storeImage(image, props);
    }
}
