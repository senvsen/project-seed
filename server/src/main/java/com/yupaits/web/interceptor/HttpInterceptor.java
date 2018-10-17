package com.yupaits.web.interceptor;

import com.yupaits.commons.utils.HttpUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http拦截器，将request对象存入ThreadLocal中
 * @author yupaits
 * @date 2018/10/16
 */
public class HttpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpUtils.setRequest(request);
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpUtils.removeRequest();
    }
}
