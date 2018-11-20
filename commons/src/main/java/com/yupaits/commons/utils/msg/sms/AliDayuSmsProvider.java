package com.yupaits.commons.utils.msg.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * 阿里大于短信平台
 * 开发文档：https://help.aliyun.com/document_detail/59210.html
 * @author yupaits
 * @date 2018/11/20
 */
@Slf4j
public class AliDayuSmsProvider extends BaseSmsProvider {

    private final String product = "";
    private final String domain = "";
    private final String endpointName = "cn-hangzhou";
    private final String regionId = "cn-hangzhou";
    private final String accessKeyId = "";
    private final String accessKeySecret = "";
    private final String signName = "";
    private final String templateCode = "";
    private final String replacementParamKey = "";
    private final String outId = "";

    @Override
    boolean sendSms(String phoneNumber, String smsContent) {
        boolean result = false;
        try {
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint(endpointName, regionId, product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendSmsRequest smsRequest = new SendSmsRequest();
            smsRequest.setMethod(MethodType.POST);
            smsRequest.setPhoneNumbers(phoneNumber);
            smsRequest.setSignName(signName);
            smsRequest.setTemplateCode(templateCode);
            JSONObject templateParam = new JSONObject();
            templateParam.put(replacementParamKey, "");
            smsRequest.setTemplateParam(templateParam.toJSONString());
            smsRequest.setOutId(outId);
            SendSmsResponse smsResponse = acsClient.getAcsResponse(smsRequest);
            if (smsResponse.getCode() != null && smsResponse.getCode().equals(HttpStatus.OK.name())) {
                result = true;
            } else {
                log.error("发送短信失败, PhoneNumber: {}, RequestId: {}, BizId: {}, ResCode: {}, ResMessage: {}",
                        phoneNumber, smsResponse.getRequestId(), smsResponse.getBizId(), smsResponse.getCode(), smsResponse.getMessage());
            }
        } catch (ClientException e) {
            log.error("发送短信失败, PhoneNumber: {}", phoneNumber, e);
        }
        return result;

    }
}
