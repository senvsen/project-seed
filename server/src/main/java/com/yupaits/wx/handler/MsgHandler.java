package com.yupaits.wx.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.commons.consts.enums.MatchRule;
import com.yupaits.wx.dto.WxMpReplyMessage;
import com.yupaits.wx.entity.MpAutoReply;
import com.yupaits.wx.service.IMpAutoReplyService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 被动回复消息
 * @author yupaits
 * @date 2018/11/12
 */
@Slf4j
@Component
public class MsgHandler implements WxMpMessageHandler {

    private final IMpAutoReplyService autoReplyService;

    private Long id;
    private List<MpAutoReply> autoReplyList;

    @Autowired
    public MsgHandler(IMpAutoReplyService autoReplyService) {
        this.autoReplyService = autoReplyService;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        log.debug("{} 向公众号 {} 发送了消息: {}", wxMpXmlMessage.getFromUser(), id, wxMpXmlMessage);
        if (StringUtils.equals(wxMpXmlMessage.getMsgType(), WxConsts.XmlMsgType.TEXT)) {
            String message = wxMpXmlMessage.getContent();
            if (CollectionUtils.isNotEmpty(autoReplyList)) {
                for (MpAutoReply autoReply : autoReplyList) {
                    List<String> keywords = JSON.parseArray(autoReply.getKeywords(), String.class);
                    if (autoReply.getMatchRule().equals(MatchRule.OR)
                            && CollectionUtils.isNotEmpty(keywords)
                            && keywords.parallelStream().anyMatch(keyword -> StringUtils.contains(message, keyword))) {
                        return JSON.parseObject(autoReply.getReply(), WxMpReplyMessage.class).replyToOutMessage(wxMpXmlMessage);
                    } else if (autoReply.getMatchRule().equals(MatchRule.AND)
                            && CollectionUtils.isNotEmpty(keywords)
                            && keywords.parallelStream().allMatch(keyword -> StringUtils.contains(message, keyword))) {
                        return JSON.parseObject(autoReply.getReply(), WxMpReplyMessage.class).replyToOutMessage(wxMpXmlMessage);
                    }
                }
            }
        }
        return null;
    }

    public WxMpMessageHandler getHandler(Long id) {
        this.id = id;
        this.autoReplyList = autoReplyService.list(new QueryWrapper<MpAutoReply>().eq("account_id", id));
        return this;
    }
}
