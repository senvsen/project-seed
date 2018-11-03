package com.yupaits.web.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yupaits
 * @date 2018/10/31
 */
public class ShiroAuthcFilter extends FormAuthenticationFilter {

    private static final String ASYNC_HEADER = "X-Requested-With";
    private static final String ASYNC_HEADER_VALUE = "XMLHttpRequest";

    public ShiroAuthcFilter() {
        super();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (isLoginRequest(request, response)) {
            return super.onAccessDenied(request, response, mappedValue);
        } else {
            if (isAsyncRequest((HttpServletRequest) request)) {
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    private boolean isAsyncRequest(HttpServletRequest request) {
        return StringUtils.equals(ASYNC_HEADER_VALUE, request.getHeader(ASYNC_HEADER));
    }
}
