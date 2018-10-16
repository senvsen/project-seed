package com.yupaits.commons.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @author yupaits
 * @date 2018/10/15
 */
@Data
@Builder
public class Result<T> {
    private int code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
