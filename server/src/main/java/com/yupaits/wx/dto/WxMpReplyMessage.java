package com.yupaits.wx.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 微信回复消息
 * @author yupaits
 * @date 2018/11/12
 */
@Data
public class WxMpReplyMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgType;
    private String content;
    private String mediaId;
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;
    private String thumbMediaId;
    private List<WxMpXmlOutNewsMessage.Item> articles = Lists.newArrayList();

    /**
     * WxMpReplyMessage转换成WxMpXmlOutMessage
     * @param imMessage 接收的消息
     * @return 回复的消息
     */
    public WxMpXmlOutMessage replyToOutMessage(WxMpXmlMessage imMessage) {
        switch (this.getMsgType()) {
            case WxConsts.XmlMsgType.TEXT:
                return WxMpXmlOutMessage.TEXT()
                        .content(content)
                        .fromUser(imMessage.getToUser()).toUser(imMessage.getFromUser())
                        .build();
            case WxConsts.XmlMsgType.IMAGE:
                return WxMpXmlOutMessage.IMAGE()
                        .mediaId(mediaId)
                        .fromUser(imMessage.getToUser()).toUser(imMessage.getFromUser())
                        .build();
            case WxConsts.XmlMsgType.VOICE:
                return WxMpXmlOutMessage.VOICE()
                        .mediaId(mediaId)
                        .fromUser(imMessage.getToUser()).toUser(imMessage.getFromUser())
                        .build();
            case WxConsts.XmlMsgType.VIDEO:
                return WxMpXmlOutMessage.VIDEO()
                        .title(title)
                        .description(description)
                        .mediaId(mediaId)
                        .fromUser(imMessage.getToUser()).toUser(imMessage.getFromUser())
                        .build();
            case WxConsts.XmlMsgType.MUSIC:
                return WxMpXmlOutMessage.MUSIC().title(title)
                        .description(description)
                        .musicUrl(musicUrl)
                        .hqMusicUrl(hqMusicUrl)
                        .thumbMediaId(thumbMediaId)
                        .fromUser(imMessage.getToUser()).toUser(imMessage.getFromUser())
                        .build();
            case WxConsts.XmlMsgType.NEWS:
                return WxMpXmlOutMessage.NEWS().articles(articles)
                        .fromUser(imMessage.getToUser()).toUser(imMessage.getFromUser())
                        .build();
            default:
                return null;
        }
    }

    public boolean isValid() {
        if (StringUtils.isBlank(msgType)) {
            return false;
        }
        switch (msgType) {
            case WxConsts.XmlMsgType.TEXT:
                return StringUtils.isNotBlank(content);
            case WxConsts.XmlMsgType.IMAGE:
                return StringUtils.isNotBlank(mediaId);
            case WxConsts.XmlMsgType.VOICE:
                return StringUtils.isNotBlank(mediaId);
            case WxConsts.XmlMsgType.VIDEO:
                return StringUtils.isNotBlank(mediaId);
            case WxConsts.XmlMsgType.MUSIC:
                return StringUtils.isNotBlank(thumbMediaId);
            case WxConsts.XmlMsgType.NEWS:
                return articlesValid(articles);
            default:
                return false;
        }
    }

    private boolean articleValid(WxMpXmlOutNewsMessage.Item article) {
        return !StringUtils.isAnyBlank(article.getTitle(), article.getDescription(), article.getUrl(), article.getPicUrl());
    }

    private boolean articlesValid(Iterable<WxMpXmlOutNewsMessage.Item> articles) {
        if (IterableUtils.isEmpty(articles)) {
            return false;
        }
        for (WxMpXmlOutNewsMessage.Item article : articles) {
            if (!articleValid(article)) {
                return false;
            }
        }
        return true;
    }

}
