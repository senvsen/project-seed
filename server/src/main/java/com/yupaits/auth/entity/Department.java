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
@TableName("auth_department")
@ApiModel(value="Department对象", description="")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门Key")
    private String departmentKey;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父级部门ID")
    private String parentId;


}
