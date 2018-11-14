package com.yupaits.wx.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * MpWelcomeMessageUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpWelcomeMessageUpdate", description = "MpWelcomeMessageUpdate对象")
public class MpWelcomeMessageUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "欢迎语ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long accountId;

    @ApiModelProperty(value = "启用标记")
    private Boolean active;

    @ApiModelProperty(value = "欢迎语内容")
    private String message;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
