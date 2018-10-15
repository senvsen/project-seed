package com.yupaits.commons.result;

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
    private T data;
}
