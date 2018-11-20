package com.yupaits.msg.helper;

import com.alibaba.fastjson.JSONObject;
import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.msg.entity.Template;
import com.yupaits.msg.service.ITemplateService;
import com.yupaits.msg.vo.MessageVO;
import com.yupaits.msg.vo.TemplateVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@Component
public class MessageHelper {

    private final ITemplateService templateService;

    @Autowired
    public MessageHelper(ITemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * 获取消息内容
     * @param messageVO 消息对象
     * @return 消息内容
     */
    public String getMessageContent(MessageVO messageVO) {
        if (messageVO.getUseTemplate() && ValidateUtils.idValid(messageVO.getMsgTemplateId())) {
            Template template = templateService.getById(messageVO.getMsgTemplateId());
            TemplateVO templateVO = new TemplateVO();
            BeanUtils.copyProperties(template, templateVO);
            templateVO.setFillFields(template.getFillFields());
            return messageContentFormat(templateVO, messageVO);
        } else {
            return messageVO.getContent();
        }
    }

    /**
     * 根据模板和参数格式化消息内容
     * @param templateVO 消息模板
     * @param messageVO 消息对象
     * @return 格式化后的消息内容
     */
    private String messageContentFormat(TemplateVO templateVO, MessageVO messageVO) {
        String content = templateVO.getTemplatePattern();
        List<String> fillFields = templateVO.getFillFields();
        JSONObject payload = messageVO.getPayload();
        if (CollectionUtils.isNotEmpty(fillFields)) {
            for (String field : fillFields) {
                if (StringUtils.isBlank(field)) {
                    continue;
                }
                content = content.replace("{{" + field + "}}", payload.getString(field));
            }
        }
        return content;
    }
}
