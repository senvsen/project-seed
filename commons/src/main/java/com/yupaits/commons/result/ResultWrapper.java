package com.yupaits.commons.result;

import org.springframework.http.HttpStatus;

/**
 * 响应结果生成
 * @author yupaits
 * @date 2018/10/15
 */
public class ResultWrapper {

    public static Result success() {
        return Result.builder()
                .code(HttpStatus.OK.value())
                .msg(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    public static <T> Result success(T data) {
        return Result.builder()
                .code(HttpStatus.OK.value())
                .msg(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    public static Result fail(HttpStatus httpStatus) {
        return Result.builder()
                .code(httpStatus.value())
                .msg(httpStatus.getReasonPhrase())
                .build();
    }

    public static Result fail(ResultCode resultCode) {
        return Result.builder()
                .code(resultCode.getCode())
                .msg(resultCode.getMsg())
                .build();
    }

    public static Result fail(String msg) {
        return Result.builder()
                .code(ResultCode.FAIL.getCode())
                .msg(msg)
                .build();
    }
}
