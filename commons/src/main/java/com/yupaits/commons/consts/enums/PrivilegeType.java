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
    MENU(1, "菜单"),
    /**
     * 按钮
     */
    BUTTON(2, "按钮"),
    /**
     * API
     */
    API(3, "API");

    @EnumValue
    private int code;
    private String label;

    PrivilegeType(int value, String label) {
        this.code = value;
        this.label = label;
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
    public String getLabel() {
        return this.label;
    }
}
