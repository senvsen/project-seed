package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author yupaits
 * @date 2018/11/9
 */
public enum MsgLevel {
    /**
     * 通知
     */
    INFO(1),
    /**
     * 告警
     */
    WARNING(2),
    /**
     * 危险
     */
    DANGER(3);

    @EnumValue
    private int code;

    MsgLevel(int code) {
        this.code = code;
    }

    @JsonCreator
    public static MsgLevel fromCode(int code) {
        for (MsgLevel level : MsgLevel.values()) {
            if (level.code == code) {
                return level;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
