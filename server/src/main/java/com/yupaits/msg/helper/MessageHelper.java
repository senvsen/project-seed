package com.yupaits.msg.helper;

import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.msg.entity.Template;
import com.yupaits.msg.service.ITemplateService;
import com.yupaits.msg.vo.MessageVO;
import com.yupaits.msg.vo.TemplateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            return parseMessageContent(templateVO, messageVO);
        } else {
            return messageVO.getContent();
        }
    }

    /**
     * 根据模板解析获取消息内容
     * @param templateVO 消息模板
     * @param messageVO 消息对象
     * @return 解析后的消息内容
     */
    private String parseMessageContent(TemplateVO templateVO, MessageVO messageVO) {
        //TODO 解析逻辑
        return "";
    }
}
