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
 * RolePrivilegeUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RolePrivilegeUpdate", description = "RolePrivilegeUpdate对象")
public class RolePrivilegeUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "角色ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long roleId;

    @ApiModelProperty(value = "权限ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long privilegeId;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
