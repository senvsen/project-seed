package com.yupaits.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 上传文件VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-07
 */
@Data
@ApiModel(value = "StoreFileVO", description = "StoreFileVO对象")
public class StoreFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "文件存储组")
    private String groupName;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "隐私文件标记")
    private Boolean privacy;

    @ApiModelProperty(value = "加密串")
    private String privacyCode;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;
}
