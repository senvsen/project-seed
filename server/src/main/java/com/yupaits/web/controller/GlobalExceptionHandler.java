package com.yupaits.web.controller;

import com.yupaits.commons.exceptions.ServiceException;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yupaits
 * @date 2018/10/19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException ex) {
        return ResultWrapper.fail(ex.getMessage());
    }
}
