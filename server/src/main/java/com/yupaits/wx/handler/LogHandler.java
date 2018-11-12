package com.yupaits.wx.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 记录所有事件日志
 * @author yupaits
 * @date 2018/11/12
 */
@Slf4j
public class LogHandler implements WxMpMessageHandler {

    private final Long id;

    public LogHandler(Long id) {
        this.id = id;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        log.info("公众号 {} 接收来自 {} 的消息: {}", id, wxMpXmlMessage.getFromUser(), wxMpXmlMessage);
        return null;
    }
}
