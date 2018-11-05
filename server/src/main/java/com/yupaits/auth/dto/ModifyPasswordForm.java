package com.yupaits.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/11/5
 */
@Data
@ApiModel(value = "ModifyPasswordForm", description = "修改密码表单对象")
public class ModifyPasswordForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "原密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;

    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return !StringUtils.isAnyBlank(oldPassword, newPassword, confirmPassword);
    }
}
