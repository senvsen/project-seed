package com.yupaits.wx.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.wx.dto.WxMpReplyMessage;
import com.yupaits.wx.entity.MpWelcomeMessage;
import com.yupaits.wx.mapper.MpWelcomeMessageMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 关注事件回复（欢迎语）
 * @author yupaits
 * @date 2018/11/12
 */
@Slf4j
@Component
public class SubscribeHandler implements WxMpMessageHandler {

    private final MpWelcomeMessageMapper welcomeMessageMapper;

    private Long id;
    private MpWelcomeMessage welcomeMessage;

    @Autowired
    public SubscribeHandler(MpWelcomeMessageMapper welcomeMessageMapper) {
        this.welcomeMessageMapper = welcomeMessageMapper;
    }

    public WxMpMessageHandler getHandler(Long id) {
        this.id = id;
        this.welcomeMessage = welcomeMessageMapper.selectOne(new QueryWrapper<MpWelcomeMessage>().eq("account_id", id));
        return this;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) {
        log.info("{}关注了公众号{}", wxMpXmlMessage.getFromUser(), id);
        if (welcomeMessage == null || !welcomeMessage.isActive()) {
            return null;
        }
        return JSON.parseObject(welcomeMessage.getMessage(), WxMpReplyMessage.class).replyToOutMessage(wxMpXmlMessage);
    }
}
