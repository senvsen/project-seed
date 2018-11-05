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
 * RoleUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleUpdate", description = "RoleUpdate对象")
public class RoleUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", required = true)
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

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
        return ValidateUtils.idValid(id) && ValidateUtils.idValid(departmentId) && !StringUtils.isAnyBlank(roleKey, name);
    }

}
