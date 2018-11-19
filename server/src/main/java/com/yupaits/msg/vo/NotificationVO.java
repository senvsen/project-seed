package com.yupaits.msg.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通知VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@ApiModel(value = "NotificationVO", description = "NotificationVO对象")
public class NotificationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "操作者ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long operator;

    @ApiModelProperty(value = "动作")
    private Integer action;

    @ApiModelProperty(value = "目标对象ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long targetId;

    @ApiModelProperty(value = "目标对象类型")
    private Integer targetType;

    @ApiModelProperty(value = "目标对象所有者")
    @JsonSerialize(using = LongSerializer.class)
    private Long targetOwner;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendAt;

    @ApiModelProperty(value = "已读标记")
    private Boolean hasRead;
}
