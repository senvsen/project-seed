package com.yupaits.commons.core.identity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 关联实体外键查询封装
 * @author yupaits
 * @date 2018/10/20
 */
@Data
@AllArgsConstructor
@ApiModel(value = "ForeignId", description = "关联实体外键")
public class ForeignId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称")
    private String fieldName;

    @ApiModelProperty(value = "属性值")
    private Serializable value;

    @ApiModelProperty(hidden = true)
    public boolean isValid(Class clazz) {
        if (StringUtils.isBlank(fieldName) || value == null) {
            return false;
        }
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
