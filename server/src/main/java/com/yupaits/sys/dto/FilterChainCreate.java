package com.yupaits.sys.dto;

import com.yupaits.commons.core.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * FilterChainCreate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FilterChainCreate", description = "FilterChainCreate对象")
public class FilterChainCreate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目标URL", required = true)
    private String url;

    @ApiModelProperty(value = "过滤器名称", required = true)
    private String filter;

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return !StringUtils.isAnyBlank(url, filter);
    }

}
