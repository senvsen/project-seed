package com.yupaits.web.mq;

import com.yupaits.commons.utils.msg.sms.AliDayuSmsProvider;
import com.yupaits.commons.utils.msg.sms.BaseSmsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yupaits
 * @date 2018/11/20
 */
@Configuration
public class MsgConfig {

    @Bean
    public BaseSmsProvider smsProvider() {
        return new AliDayuSmsProvider();
    }
}
