package com.yupaits.commons.consts.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 消息类型
 * @author yupaits
 * @date 2018/11/8
 */
public enum MsgType {
    /**
     * 站内信
     */
    SITE(1),
    /**
     * 邮件
     */
    EMAIL(2),
    /**
     * 短信
     */
    SMS(3),
    /**
     * 微信推送消息
     */
    WECHAT(4);

    @EnumValue
    private int code;

    MsgType(int code) {
        this.code = code;
    }

    @JsonCreator
    public static MsgType fromCode(int code) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.code == code) {
                return msgType;
            }
        }
        return null;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }
}
