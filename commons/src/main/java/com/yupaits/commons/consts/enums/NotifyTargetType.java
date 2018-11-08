package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author yupaits
 * @date 2018/11/8
 */
public enum NotifyTargetType {
    ;

    @EnumValue
    private int code;

    NotifyTargetType(int code) {
        this.code = code;
    }

    @JsonCreator
    public static NotifyTargetType fromCode(int code) {
        for (NotifyTargetType targetType : NotifyTargetType.values()) {
            if (targetType.code == code) {
                return targetType;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
