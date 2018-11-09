package com.yupaits.web.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@Configuration
public class MqConfig {

    static final String EXCHANGE = "seed.msg";
    static final String NOTIFICATION_ROUTING_KEY = "msg.notification";
    static final String MESSAGE_ROUTING_KEY = "msg.message";
    static final String NOTIFICATION_QUEUE = "seed.notification";
    static final String MESSAGE_QUEUE = "seed.message";

    @Bean
    public Queue notificationQueue() {
        //TODO 设置队列延迟重试
        return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
    }

    @Bean
    public Queue messageQueue() {
        //TODO 设置队列延迟重试
        return QueueBuilder.durable(MESSAGE_QUEUE).build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).build();
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(exchange()).with(NOTIFICATION_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue()).to(exchange()).with(MESSAGE_ROUTING_KEY).noargs();
    }
}
