package com.yupaits.commons.utils.msg;

/**
 * @author yupaits
 * @date 2018/11/9
 */
public abstract class BaseSender<M extends MsgModel> {

    /**
     * 发送消息
     * @param msg 消息
     * @return 发送结果
     */
    protected abstract boolean send(M msg);
}
