package com.yupaits.web.mq;

import com.yupaits.auth.entity.User;
import com.yupaits.auth.service.IUserService;
import com.yupaits.commons.utils.msg.email.EmailMsg;
import com.yupaits.commons.utils.msg.email.EmailSender;
import com.yupaits.commons.utils.msg.sms.SmsMsg;
import com.yupaits.commons.utils.msg.sms.SmsSender;
import com.yupaits.msg.entity.Message;
import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.entity.Notification;
import com.yupaits.msg.helper.MessageHelper;
import com.yupaits.msg.service.IMessageService;
import com.yupaits.msg.service.IMessageUserService;
import com.yupaits.msg.service.INotificationService;
import com.yupaits.msg.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@Slf4j
@Component
@RabbitListener(bindings = {
        @QueueBinding(value = @Queue(MqConfig.NOTIFICATION_QUEUE), exchange = @Exchange(MqConfig.EXCHANGE), key = MqConfig.NOTIFICATION_ROUTING_KEY),
        @QueueBinding(value = @Queue(MqConfig.MESSAGE_QUEUE), exchange = @Exchange(MqConfig.EXCHANGE), key = MqConfig.MESSAGE_ROUTING_KEY)
})
public class MsgReceiver {

    private final INotificationService notificationService;
    private final IMessageUserService messageUserService;
    private final IMessageService messageService;
    private final IUserService userService;
    private final MessageHelper messageHelper;
    private final JavaMailSenderImpl javaMailSender;

    @Autowired
    public MsgReceiver(INotificationService notificationService, IMessageUserService messageUserService,
                       IMessageService messageService, IUserService userService, MessageHelper messageHelper,
                       JavaMailSenderImpl javaMailSender) {
        this.notificationService = notificationService;
        this.messageUserService = messageUserService;
        this.messageService = messageService;
        this.userService = userService;
        this.messageHelper = messageHelper;
        this.javaMailSender = javaMailSender;
    }

    @RabbitHandler
    public void handleNotification(Notification notification) {
        notificationService.save(notification);
    }

    @RabbitHandler
    public void handleMessage(MessageUser messageUser) {
        Message message = messageService.getById(messageUser.getMessageId());
        if (message == null) {
            messageUserService.removeById(messageUser.getId());
            return;
        }
        MessageVO messageVO = new MessageVO();
        BeanUtils.copyProperties(message, messageVO);
        messageVO.setPayload(message.getPayload());
        String msgContent = messageHelper.getMessageContent(messageVO);
        User user = userService.getById(messageUser.getUserId());
        boolean result = true;
        try {
            switch (message.getMsgType()) {
                case SMS:
                    SmsSender smsSender = new SmsSender();
                    SmsMsg smsMsg = new SmsMsg().setSender("")
                            .setReceiver(user.getPhone())
                            .setContent(msgContent);
                    smsSender.send(smsMsg);
                    break;
                case EMAIL:
                    EmailSender emailSender = new EmailSender(javaMailSender);
                    EmailMsg emailMsg = new EmailMsg().setSender(javaMailSender.getUsername())
                            .setReceiver(user.getEmail())
                            .setSubject(messageVO.getPayload().getString("subject"))
                            .setContent(msgContent);
                    emailSender.send(emailMsg);
                    break;
                case WECHAT:

                    break;
                default:
                    result = false;
            }
        } catch (Exception e) {
            log.error("处理消息失败, 消息-用户: {}", messageUser, e);
            result = false;
        }
        if (result) {
            messageUser.setSendAt(LocalDateTime.now()).setDeleted(true);
            messageUserService.saveOrUpdate(messageUser);
            if (messageUser.isNeedRemove()) {
                messageService.removeById(messageUser.getMessageId());
            }
        } else {
            //TODO 消息消费失败处理
        }
    }
}
