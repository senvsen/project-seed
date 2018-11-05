package com.yupaits.auth.dto;

import com.yupaits.commons.consts.enums.PrivilegeType;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * PrivilegeCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PrivilegeCreate", description = "PrivilegeCreate对象")
public class PrivilegeCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限类型", required = true)
    private PrivilegeType privilegeType;

    @ApiModelProperty(value = "权限Key", required = true)
    private String privilegeKey;

    @ApiModelProperty(value = "权限名", required = true)
    private String name;

    @ApiModelProperty(value = "权限描述", allowEmptyValue = true)
    private String description;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return privilegeType != null && !StringUtils.isAnyBlank(privilegeKey, name);
    }

}
