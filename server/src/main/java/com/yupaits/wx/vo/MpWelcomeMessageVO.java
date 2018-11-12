package com.yupaits.wx.vo;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公众号欢迎语VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@ApiModel(value = "MpWelcomeMessageVO", description = "MpWelcomeMessageVO对象")
public class MpWelcomeMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "欢迎语ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long accountId;

    @ApiModelProperty(value = "启用标记")
    private Boolean active;

    @ApiModelProperty(value = "欢迎语内容")
    private String message;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "更新人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long updatedBy;
}
