package com.yupaits.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * DepartmentCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DepartmentCreate", description = "DepartmentCreate对象")
public class DepartmentCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门Key", required = true)
    private String departmentKey;

    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @ApiModelProperty(value = "父级部门ID", required = true)
    @JsonDeserialize(using = LongDeserializer.class)
    private Long parentId;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return parentId != null && parentId.compareTo(0L) >= 0 && !StringUtils.isAnyBlank(departmentKey, name);
    }

}
