package com.yupaits.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.consts.enums.PrivilegeType;
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
 * PrivilegeUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PrivilegeUpdate", description = "PrivilegeUpdate对象")
public class PrivilegeUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID", required = true)
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

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
        return ValidateUtils.idValid(id) && privilegeType != null && !StringUtils.isAnyBlank(privilegeKey, name);
    }

}
