package com.yupaits.sys.dto;

import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * FilterChainCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FilterChainCreate", description = "FilterChainCreate对象")
public class FilterChainCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目标URL")
    private String url;

    @ApiModelProperty(value = "过滤器名称")
    private String filter;

    @ApiModelProperty(value = "排序码")
    private Integer sortCode;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
