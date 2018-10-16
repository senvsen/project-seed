package com.yupaits.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupaits.commons.exceptions.ServiceException;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 *
 * @author yupaits
 * @date 2018/10/16
 */
@Slf4j
public class ExceptionHandler implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper;

    public ExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof ServiceException) {
            responseResult(response, ResultWrapper.fail(ex.getMessage()));
        }
        return new ModelAndView();
    }

    /**
     * 将响应结果写入response
     */
    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());
        try {
            objectMapper.writeValue(response.getWriter(), result);
        } catch (IOException e) {
            log.error("异常处理返回响应结果出错", e);
        }
    }
}
