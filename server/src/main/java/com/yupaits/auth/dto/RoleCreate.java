package com.yupaits.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.commons.core.serializer.LongDeserializer;
import com.yupaits.commons.utils.ValidateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * RoleCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleCreate", description = "RoleCreate对象")
public class RoleCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色Key", required = true)
    private String roleKey;

    @ApiModelProperty(value = "角色名", required = true)
    private String name;

    @ApiModelProperty(value = "角色描述", allowEmptyValue = true)
    private String description;

    @ApiModelProperty(value = "所属部门ID", required = true)
    @JsonDeserialize(using = LongDeserializer.class)
    private Long departmentId;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return ValidateUtils.idValid(departmentId) && !StringUtils.isAnyBlank(roleKey, name);
    }

}
