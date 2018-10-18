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
    FAIL(10000, ""),
    PARAMS_ERROR(10001, "参数校验失败"),
    DATA_NOT_FOUND(10002, "查找的数据不存在或已被删除"),
    DATA_CANNOT_DELETE(10003, "数据无法删除"),
    DATA_CONFLICT(10004, "意外的数据冲突");

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
