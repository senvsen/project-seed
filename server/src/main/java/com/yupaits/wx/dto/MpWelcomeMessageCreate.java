package com.yupaits.wx.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;

/**
 * <p>
 * MpWelcomeMessageCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpWelcomeMessageCreate", description = "MpWelcomeMessageCreate对象")
public class MpWelcomeMessageCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long accountId;

    @ApiModelProperty(value = "启用标记")
    private Boolean active;

    @ApiModelProperty(value = "欢迎语内容")
    private WxMpMassPreviewMessage message;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }


    public String getMessage() {
        return JSON.toJSONString(message);
    }

}
