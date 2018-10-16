package com.yupaits.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yupaits.commons.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yupaits
 * @since 2018-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("auth_privilege")
@ApiModel(value="Privilege对象", description="")
public class Privilege extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限类型")
    private Integer privilegeType;

    @ApiModelProperty(value = "权限Key")
    private String privilegeKey;

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "权限描述")
    private String description;


}
