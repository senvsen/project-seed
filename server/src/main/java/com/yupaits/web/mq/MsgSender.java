package com.yupaits.web.mq;

import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.entity.Notification;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@Component
public class MsgSender {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MsgSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(Notification notification) {
        amqpTemplate.convertAndSend(MqConfig.EXCHANGE, MqConfig.NOTIFICATION_ROUTING_KEY, notification);
    }

    public void send(MessageUser messageUser) {
        amqpTemplate.convertAndSend(MqConfig.EXCHANGE, MqConfig.MESSAGE_ROUTING_KEY, messageUser);
    }
}
