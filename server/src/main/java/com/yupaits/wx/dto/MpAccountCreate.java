package com.yupaits.wx.dto;

import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * MpAccountCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpAccountCreate", description = "MpAccountCreate对象")
public class MpAccountCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "关联类型")
    private Integer relatedType;

    @ApiModelProperty(value = "关联ID")
    private String relatedId;

    @ApiModelProperty(value = "公众号名称")
    private String appName;

    @ApiModelProperty(value = "公众号AppId")
    private String appId;

    @ApiModelProperty(value = "公众号Secret")
    private String appSecret;

    @ApiModelProperty(value = "公众号Token")
    private String token;

    @ApiModelProperty(value = "公众号EncodingAesKey")
    private String encodingAesKey;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
