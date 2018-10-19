package com.yupaits.commons.result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

/**
 * Mvc Model封装
 * @author yupaits
 * @date 2018/10/19
 */
public class ModelWrapper {

    private static final String OK = "ok";
    private static final String MESSAGE = "msg";
    private static final String DATA = "data";

    public static void success(Model model) {
        buildModel(model, true, null, null);
    }

    public static <T> void success(Model model, T data) {
        buildModel(model, true, null, data);
    }

    public static void fail(Model model) {
        buildModel(model, false, null, null);
    }

    public static void fail(Model model, ResultCode resultCode) {
        buildModel(model, false, resultCode.getMsg(), null);
    }

    public static void fail(Model model, String message) {
        buildModel(model, false, message, null);
    }

    public static <T> void fail(Model model, ResultCode resultCode, T data) {
        buildModel(model, false, resultCode.getMsg(), data);
    }

    private static <T> void buildModel(Model model, boolean ok, String message, T data) {
        model.addAttribute(OK, ok);
        if (StringUtils.isNotBlank(message)) {
            model.addAttribute(MESSAGE, message);
        }
        if (data != null) {
            model.addAttribute(DATA, data);
        }
    }
}
