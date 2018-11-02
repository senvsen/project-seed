package com.yupaits.commons.core.identity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author yupaits
 * @date 2018/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ForeignIdCollection", description = "次关联键集合")
public class ForeignIdCollection<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称")
    private String fieldName;

    @ApiModelProperty(value = "属性值")
    private List<T> values;

    @ApiModelProperty(hidden = true)
    public boolean isValid(Class clazz) {
        if (StringUtils.isBlank(fieldName) || values == null) {
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
