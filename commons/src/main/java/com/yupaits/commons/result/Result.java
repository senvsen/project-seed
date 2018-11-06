package com.yupaits.commons.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/10/15
 */
@Data
@Builder
@ApiModel
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("返回码")
    private int code;

    @ApiModelProperty("返回信息")
    private String msg;

    @ApiModelProperty("返回数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
