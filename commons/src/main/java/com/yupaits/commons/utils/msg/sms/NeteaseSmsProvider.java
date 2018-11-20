package com.yupaits.commons.utils.msg.sms;

/**
 * 网易云信短信平台
 * 开发文档：http://dev.yunxin.163.com/docs/product/短信/短信接口指南
 * @author yupaits
 * @date 2018/11/20
 */
public class NeteaseSmsProvider extends BaseSmsProvider {
    @Override
    boolean sendSms(String phoneNumber, String smsContent) {
        return false;
    }
}
