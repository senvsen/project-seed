package com.yupaits.commons.utils.msg.sms;

/**
 * @author yupaits
 * @date 2018/11/20
 */
public abstract class BaseSmsProvider {

    /**
     * 发送短信
     * @param phoneNumber 目标手机号码
     * @param smsContent 短信内容
     * @return 发送短信结果 true-发送成功
     */
    abstract boolean sendSms(String phoneNumber, String smsContent);
}
