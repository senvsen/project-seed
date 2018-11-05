package com.yupaits.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * DepartmentUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DepartmentUpdate", description = "DepartmentUpdate对象")
public class DepartmentUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "部门Key")
    private String departmentKey;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父级部门ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long parentId;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
