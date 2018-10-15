package com.yupaits.commons.exceptions;

/**
 * 服务（业务）异常
 * @author yupaits
 * @date 2018/10/15
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
