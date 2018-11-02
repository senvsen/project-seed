package com.yupaits.commons.core.identity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 关联实体查询封装
 * @author yupaits
 * @date 2018/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RelatedId", description = "关联键组合")
public class RelatedId<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主关联键")
    private ForeignId<T> firstId;

    @ApiModelProperty(value = "次关联键集合")
    private ForeignIdCollection<T> secondIds;

    @ApiModelProperty(hidden = true)
    public boolean isValid(Class clazz) {
        return firstId != null && secondIds != null && firstId.isValid(clazz) && secondIds.isValid(clazz);
    }
}
