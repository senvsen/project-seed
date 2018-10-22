package com.yupaits.commons.result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Mvc Model封装
 * @author yupaits
 * @date 2018/10/19
 */
public class ModelWrapper {

    private static final String OK = "ok";
    private static final String MESSAGE = "msg";
    private static final String DATA = "data";

    public static <M extends Model> void success(M model) {
        buildModel(model, true, null, null);
    }

    public static <M extends Model, T> void success(M model, T data) {
        buildModel(model, true, null, data);
    }

    public static <M extends Model> void fail(M model) {
        buildModel(model, false, null, null);
    }

    public static <M extends Model> void fail(M model, ResultCode resultCode) {
        buildModel(model, false, resultCode.getMsg(), null);
    }

    public static <M extends Model> void fail(M model, String message) {
        buildModel(model, false, message, null);
    }

    public static <M extends Model, T> void fail(M model, ResultCode resultCode, T data) {
        buildModel(model, false, resultCode.getMsg(), data);
    }

    private static <M extends Model, T> void buildModel(M model, boolean ok, String message, T data) {
        if (model instanceof RedirectAttributes) {
            ((RedirectAttributes) model).addFlashAttribute(OK, ok);
            if (StringUtils.isNotBlank(message)) {
                ((RedirectAttributes) model).addFlashAttribute(MESSAGE, message);
            }
            if (data != null) {
                ((RedirectAttributes) model).addFlashAttribute(DATA, data);
            }
        } else {
            model.addAttribute(OK, ok);
            if (StringUtils.isNotBlank(message)) {
                model.addAttribute(MESSAGE, message);
            }
            if (data != null) {
                model.addAttribute(DATA, data);
            }
        }
    }
}
