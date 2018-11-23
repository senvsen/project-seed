package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author yupaits
 * @date 2018/11/23
 */
public enum MatchRule {
    /**
     * 包含所有
     */
    AND(1),
    /**
     * 包含任一个
     */
    OR(2);

    @EnumValue
    private int code;

    MatchRule(int code) {
        this.code = code;
    }

    @JsonCreator
    public static MatchRule fromCode(int code) {
        for (MatchRule matchRule : MatchRule.values()) {
            if (matchRule.code == code) {
                return matchRule;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
