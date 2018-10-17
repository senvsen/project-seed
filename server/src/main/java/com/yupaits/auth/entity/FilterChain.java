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
 * @since 2018-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("auth_filter_chain")
@ApiModel(value="FilterChain对象", description="")
public class FilterChain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目标URL")
    private String url;

    @ApiModelProperty(value = "过滤器名称")
    private String filter;

    @ApiModelProperty(value = "排序码")
    private Integer sortCode;


}
