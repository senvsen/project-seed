package com.yupaits.sys.service;

import com.yupaits.commons.result.Result;
import com.yupaits.sys.dto.ImageProps;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yupaits
 * @date 2018/11/7
 */
public interface StorageService {

    /**
     * 保存文件
     * @param file 文件
     * @param isPrivacy 是否需要保密
     * @return 文件访问路径
     */
    Result store(MultipartFile file, boolean isPrivacy);

    /**
     * 批量保存文件
     * @param files 多个文件
     * @param isPrivacy 是否保密
     * @return 多个文件访问路径

     */
    Result batchStore(List<MultipartFile> files, boolean isPrivacy);

    /**
     * 保存图片类型文件
     * @param image 图片文件
     * @param thumbnailWidth 缩略图宽度
     * @param thumbnailHeight 缩略图高度
     * @param quality 图片质量（压缩比，如0.8表示压缩比为80%）
     * @param isPrivacy 是否保密
     * @param saveOrigin 保存原图
     * @param saveWithThumbnail 保存缩略图
     * @param saveWithWatermark 保存水印图
     * @return 图片文件访问路径
     */
    Result storeImage(MultipartFile image, int thumbnailWidth, int thumbnailHeight, float quality, boolean isPrivacy,
                      boolean saveOrigin, boolean saveWithThumbnail, boolean saveWithWatermark);

    /**
     * 保存图片类型文件
     * @param image 图片文件
     * @param props 配置参数
     * @return 图片文件访问路径
     */
    Result storeImage(MultipartFile image, ImageProps props);
}
