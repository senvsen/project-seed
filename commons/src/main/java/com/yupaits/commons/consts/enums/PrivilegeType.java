package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author yupaits
 * @date 2018/11/2
 */
public enum PrivilegeType {
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2),
    /**
     * API
     */
    API(3);

    @EnumValue
    private int code;

    PrivilegeType(int code) {
        this.code = code;
    }

    @JsonCreator
    public static PrivilegeType fromCode(int code) {
        for (PrivilegeType privilegeType : PrivilegeType.values()) {
            if (privilegeType.code == code) {
                return privilegeType;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
