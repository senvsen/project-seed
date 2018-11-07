package com.yupaits.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/11/7
 */
@Data
@AllArgsConstructor
@ApiModel("图片上传属性")
public class ImageProps implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "缩略图宽度")
    private int thumbnailWidth = 100;

    @ApiModelProperty(value = "缩略图高度")
    private int thumbnailHeight = 100;

    @ApiModelProperty(value = "图片质量（压缩比，如0.8表示压缩比为80%）")
    private float quality = 1.0f;

    @ApiModelProperty(value = "是否保密")
    private boolean isPrivacy;

    @ApiModelProperty(value = "保存原图")
    private boolean saveOrigin = true;

    @ApiModelProperty(value = "保存缩略图")
    private boolean saveWithThumbnail;

    @ApiModelProperty(value = "保存水印图")
    private boolean saveWithWatermark;

    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return (saveOrigin || (saveWithThumbnail || saveWithWatermark))
                && (!saveWithThumbnail || (thumbnailWidth > 0 && thumbnailHeight > 0 && quality > 0f && quality <= 1.0f));

    }
}
