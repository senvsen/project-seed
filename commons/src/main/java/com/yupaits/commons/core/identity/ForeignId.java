package com.yupaits.commons.core.identity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 关联实体外键查询封装
 * @author yupaits
 * @date 2018/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ForeignId", description = "关联实体外键")
public class ForeignId<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称", required = true)
    private String fieldName;

    @ApiModelProperty(value = "属性值", required = true)
    private T value;

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
