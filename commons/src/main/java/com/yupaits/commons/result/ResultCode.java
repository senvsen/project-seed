package com.yupaits.commons.result;

/**
 * 扩展的响应码和响应语义枚举，默认的响应码参考 org.springframework.http.HttpStatus
 * @author yupaits
 * @date 2018/10/15
 */

public enum ResultCode {
    /**
     * 默认的失败响应码
     */
    FAIL(10000, "FAIL");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
