package com.yupaits.wx.controller;

import com.yupaits.commons.annotation.SwaggerIgnore;
import com.yupaits.wx.helper.WxMpServiceHelper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yupaits
 * @date 2018/11/12
 */
@Slf4j
@SwaggerIgnore
@RestController
@RequestMapping("/wx/portal/{id}")
public class PortalController {

    private final WxMpServiceHelper wxMpServiceHelper;
    private static final String ENCRYPT_TYPE_AES = "aes";

    @Autowired
    public PortalController(WxMpServiceHelper wxMpServiceHelper) {
        this.wxMpServiceHelper = wxMpServiceHelper;
    }

    @GetMapping(produces = "text/plain; charset=utf-8")
    public String authGet(@PathVariable Long id,
                          @RequestParam("signature") String signature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestParam("echostr") String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：[{},{},{},{}]", signature, timestamp, nonce, echostr);
        if (wxMpServiceHelper.wxMpService(id).checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=utf-8")
    public String post(@PathVariable Long id,
                       @RequestBody String requestBody,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("signature") String signature,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature",required = false) String msgSignature) {
        log.debug("\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}], timestamp=[{}], nonce=[{}], " +
                "requestBody=[\n{}\n] ", signature, encType, msgSignature, timestamp, nonce, requestBody);
        if (!wxMpServiceHelper.wxMpService(id).checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        String out  = null;
        if (encType == null) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = wxMpServiceHelper.wxMpMessageRouter(id).route(inMessage);
            out = outMessage == null ? "" : outMessage.toXml();
        } else if (ENCRYPT_TYPE_AES.equalsIgnoreCase(encType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
                    wxMpServiceHelper.wxMpService(id).getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{}", inMessage.toString());
            WxMpXmlOutMessage outMessage = wxMpServiceHelper.wxMpMessageRouter(id).route(inMessage);
            out = outMessage == null ? "" : outMessage.toEncryptedXml(wxMpServiceHelper.wxMpService(id).getWxMpConfigStorage());
        }
        log.debug("\n回复信息：{}", out);
        return out;
    }

}
