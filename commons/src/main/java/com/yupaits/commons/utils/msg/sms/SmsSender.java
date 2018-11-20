package com.yupaits.commons.utils.msg.sms;

import com.yupaits.commons.utils.msg.BaseSender;

/**
 * @author yupaits
 * @date 2018/11/9
 */
public class SmsSender extends BaseSender<SmsMsg> {

    private final BaseSmsProvider smsProvider;

    public SmsSender(BaseSmsProvider smsProvider) {
        this.smsProvider = smsProvider;
    }

    @Override
    public boolean send(SmsMsg msg) {
        return smsProvider.sendSms(msg.getReceiver(), msg.getContent());
    }
}
