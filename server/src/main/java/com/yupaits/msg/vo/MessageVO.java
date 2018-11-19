package com.yupaits.msg.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 消息VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@ApiModel(value = "MessageVO", description = "MessageVO对象")
public class MessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "使用消息模板")
    private Boolean useTemplate;

    @ApiModelProperty(value = "消息模板ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long msgTemplateId;

    @ApiModelProperty(value = "模板参数")
    private String payload;

    @ApiModelProperty(value = "消息内容")
    private String content;

    public JSONObject getPayload() {
        return JSON.parseObject(payload);
    }
}
