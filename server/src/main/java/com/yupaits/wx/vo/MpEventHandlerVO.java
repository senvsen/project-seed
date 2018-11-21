package com.yupaits.wx.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 公众号事件处理VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@ApiModel(value = "MpEventHandlerVO", description = "MpEventHandlerVO对象")
public class MpEventHandlerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件处理ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long accountId;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "处理类路径")
    private String handlerClass;

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
