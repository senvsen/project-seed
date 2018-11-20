package com.yupaits.commons.utils.msg;

/**
 * 通用消息模型
 * @author yupaits
 * @date 2018/11/9
 */
public interface MsgModel<T> {

    /**
     * 消息接收者
     * @return 接收者
     */
    String getReceiver();

    /**
     * 消息内容
     * @return 内容
     */
    T getContent();
}
