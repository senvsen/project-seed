package com.yupaits.commons.core.identity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 关联实体查询封装
 * @author yupaits
 * @date 2018/10/20
 */
@Data
@AllArgsConstructor
@ApiModel(value = "RelatedId", description = "关联键组合")
public class RelatedId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主关联键")
    private ForeignId firstId;

    @ApiModelProperty(value = "次关联键集合")
    private ForeignIdCollection secondIds;

    @ApiModelProperty(hidden = true)
    public boolean isValid(Class clazz) {
        return firstId != null && secondIds != null && firstId.isValid(clazz) && secondIds.isValid(clazz);
    }
}
