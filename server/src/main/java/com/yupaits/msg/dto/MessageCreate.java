package com.yupaits.msg.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.consts.enums.MsgType;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.utils.ValidateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * MessageCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MessageCreate", description = "MessageCreate对象")
public class MessageCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息类型", required = true)
    private MsgType msgType;

    @ApiModelProperty(value = "使用消息模板")
    private boolean useTemplate;

    @ApiModelProperty(value = "消息模板ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long msgTemplateId;

    @ApiModelProperty(value = "模板参数")
    private JSONObject payload;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return msgType != null && (useTemplate ? ValidateUtils.idValid(msgTemplateId) && CollectionUtils.isNotEmpty(payload)
                : StringUtils.isNotBlank(content));
    }

    public String getPayload() {
        return JSON.toJSONString(payload);
    }
}
