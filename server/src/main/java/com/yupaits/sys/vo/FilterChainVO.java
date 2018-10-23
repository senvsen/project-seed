package com.yupaits.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限过滤链VO
 * </p>
 *
 * @author yupaits
 * @date 2018-10-23
 */
@Data
@ApiModel(value = "FilterChainVO", description = "FilterChainVO对象")
public class FilterChainVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限过滤链ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "目标URL")
    private String url;

    @ApiModelProperty(value = "过滤器名称")
    private String filter;

    @ApiModelProperty(value = "排序码")
    private Integer sortCode;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "更新人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long updatedBy;
}
