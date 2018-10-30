package com.yupaits.auth.dto;

import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * DepartmentCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DepartmentCreate", description = "DepartmentCreate对象")
public class DepartmentCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门Key")
    private String departmentKey;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父级部门ID")
    private String parentId;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
