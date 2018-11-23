package com.yupaits.wx.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author yupaits
 * @date 2018/11/22
 */
public class TestLocationHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) {
        return WxMpXmlOutMessage.TEXT()
                .content(String.format("当前时间：%s，%n经度：%s，%n纬度：%s，%n精度：%s", LocalDateTime.now(), wxMpXmlMessage.getLongitude(), wxMpXmlMessage.getLatitude(), wxMpXmlMessage.getPrecision()))
                .fromUser(wxMpXmlMessage.getFromUser())
                .toUser(wxMpXmlMessage.getToUser())
                .build();
    }
}
