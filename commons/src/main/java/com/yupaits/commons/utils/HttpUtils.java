package com.yupaits.commons.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yupaits
 * @date 2018/10/16
 */
public class HttpUtils {

    private static final String SESSION_USER_KEY = "";
    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        if (request != null) {
            requestThreadLocal.set(request);
        }
    }

    public static void removeRequest() {
        requestThreadLocal.remove();
    }

    public static HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        HttpSession session = null;
        if (request != null) {
            session = request.getSession();
        }
        return session;
    }

    public static Long getUserId() {
        HttpSession session = getSession();
        Long userId = null;
        if (session != null) {
            userId = (Long) session.getAttribute(SESSION_USER_KEY);
        }
        return userId;
    }
}
