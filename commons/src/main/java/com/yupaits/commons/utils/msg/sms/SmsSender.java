package com.yupaits.commons.utils.msg.sms;

import com.yupaits.commons.utils.msg.BaseSender;

/**
 * @author yupaits
 * @date 2018/11/9
 */
public class SmsSender extends BaseSender<SmsMsg> {

    @Override
    public boolean send(SmsMsg msg) {
        return false;
    }
}
