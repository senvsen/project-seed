package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author yupaits
 * @date 2018/11/8
 */
public enum NotifyAction {
    ;

    @EnumValue
    private int code;

    NotifyAction(int code) {
        this.code = code;
    }

    @JsonCreator
    public static NotifyAction fromCode(int code) {
        for (NotifyAction action : NotifyAction.values()) {
            if (action.code == code) {
                return action;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
