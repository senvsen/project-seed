package com.yupaits.msg.vo;

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
 * 消息-用户VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@ApiModel(value = "MessageUserVO", description = "MessageUserVO对象")
public class MessageUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "消息ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long messageId;

    @ApiModelProperty(value = "目标用户ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendAt;
}
