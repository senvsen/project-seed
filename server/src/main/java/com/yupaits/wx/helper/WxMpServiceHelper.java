package com.yupaits.wx.helper;

import com.yupaits.wx.handler.LogHandler;
import com.yupaits.wx.handler.MsgHandler;
import com.yupaits.wx.handler.SubscribeHandler;
import com.yupaits.wx.mapper.MpAccountMapper;
import com.yupaits.wx.service.impl.BaseWxServiceImpl;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.impl.WxMpMenuServiceImpl;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 多个公众号使用同一套逻辑的Helper, 根据公众号id来绑定事件和处理逻辑
 * @author yupaits
 * @date 2018/11/12
 */
@Component
public class WxMpServiceHelper {

    private final MpAccountMapper mpAccountMapper;
    private final SubscribeHandler subscribeHandler;
    private final MsgHandler msgHandler;
    private final MpEventHandlerHelper mpEventHandlerHelper;

    @Autowired
    public WxMpServiceHelper(MpAccountMapper mpAccountMapper, SubscribeHandler subscribeHandler, MsgHandler msgHandler,
                             MpEventHandlerHelper mpEventHandlerHelper) {
        this.mpAccountMapper = mpAccountMapper;
        this.subscribeHandler = subscribeHandler;
        this.msgHandler = msgHandler;
        this.mpEventHandlerHelper = mpEventHandlerHelper;
    }

    public BaseWxServiceImpl wxMpService(Long id) {
        return new BaseWxServiceImpl(id, mpAccountMapper);
    }

    public WxMpMenuServiceImpl wxMpMenuService(Long id) {
        return new WxMpMenuServiceImpl(wxMpService(id));
    }

    public WxMpMessageRouter wxMpMessageRouter(Long id) {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService(id));

        //记录所有事件的日志
        router.rule().handler(new LogHandler(id)).next();

        //关注公众号
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler.getHandler(id)).end();

        //自定义处理逻辑
        Map<String, WxMpMessageHandler> mpHandlerMap = mpEventHandlerHelper.fetchHandlerMap(id);
        if (MapUtils.isNotEmpty(mpHandlerMap)) {
            for (Map.Entry<String, WxMpMessageHandler> handler : mpHandlerMap.entrySet()) {
                router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(handler.getKey()).handler(handler.getValue()).end();
            }
        }

        //自动回复消息
        router.rule().async(false).handler(msgHandler.getHandler(id)).end();

        return router;
    }
}
