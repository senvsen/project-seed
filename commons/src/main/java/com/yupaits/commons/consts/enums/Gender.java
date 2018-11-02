package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author yupaits
 * @date 2018/10/16
 */
public enum Gender {
    /**
     * 男
     */
    MALE(1),
    /**
     * 女
     */
    FEMALE(0),
    /**
     * 保密
     */
    SECRET(2);

    @EnumValue
    private int code;

    Gender(int code) {
        this.code = code;
    }

    @JsonCreator
    public static Gender fromCode(int code) {
        for (Gender gender : Gender.values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
