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
 * PrivilegeUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PrivilegeUpdate", description = "PrivilegeUpdate对象")
public class PrivilegeUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "权限类型")
    private Integer privilegeType;

    @ApiModelProperty(value = "权限Key")
    private String privilegeKey;

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
