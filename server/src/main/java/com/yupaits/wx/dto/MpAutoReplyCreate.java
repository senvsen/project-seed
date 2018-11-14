package com.yupaits.wx.dto;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.wx.entity.MpAutoReply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * MpAutoReplyCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpAutoReplyCreate", description = "MpAutoReplyCreate对象")
public class MpAutoReplyCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long accountId;

    @ApiModelProperty(value = "关键词")
    private String keywords;

    @ApiModelProperty(value = "匹配规则")
    private MpAutoReply.MatchRule matchRule;

    @ApiModelProperty(value = "回复内容")
    private String reply;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = JSON.toJSONString(keywords);
    }

    public void setReply(WxMpReplyMessage reply) {
        this.reply = JSON.toJSONString(reply);
    }
}
