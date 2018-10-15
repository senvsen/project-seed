package com.yupaits.commons.core;

import com.yupaits.commons.utils.HttpUtils;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yupaits
 * @date 2018/10/16
 */
public class BaseController {

    @InitBinder
    protected void initBinder(HttpServletRequest request) {
        HttpUtils.setRequest(request);
    }
}
