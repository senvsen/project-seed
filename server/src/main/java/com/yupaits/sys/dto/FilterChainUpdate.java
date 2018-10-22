package com.yupaits.sys.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yupaits.commons.core.BaseDTO;
import com.yupaits.commons.core.serializer.LongDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * FilterChainUpdate
 * </p>
 *
 * @author yupaits
 * @date 2018-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FilterChainUpdate", description = "FilterChainUpdate对象")
public class FilterChainUpdate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限过滤链ID")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

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
