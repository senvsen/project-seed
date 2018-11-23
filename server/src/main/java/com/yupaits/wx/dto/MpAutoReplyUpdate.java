package com.yupaits.wx.dto;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.consts.enums.MatchRule;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.commons.utils.ValidateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

/**
 * <p>
 * MpAutoReplyUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpAutoReplyUpdate", description = "MpAutoReplyUpdate对象")
public class MpAutoReplyUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自动回复ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "关键词")
    private List<String> keywords;

    @ApiModelProperty(value = "匹配规则")
    private MatchRule matchRule;

    @ApiModelProperty(value = "回复内容")
    private WxMpReplyMessage reply;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return ValidateUtils.idValid(id) && !CollectionUtils.isEmpty(keywords)
                && matchRule != null && reply.isValid();
    }

    public String getKeywords() {
        return JSON.toJSONString(keywords);
    }

    public String getReply() {
        return JSON.toJSONString(reply);
    }
}
