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
 * RoleCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleCreate", description = "RoleCreate对象")
public class RoleCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色Key")
    private String roleKey;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "所属部门ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long departmentId;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}