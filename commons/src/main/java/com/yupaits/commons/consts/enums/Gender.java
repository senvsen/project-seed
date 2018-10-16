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
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(0, "女"),
    /**
     * 保密
     */
    SECRET(2, "保密");

    @EnumValue
    private int value;
    private String label;

    Gender(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        return null;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
