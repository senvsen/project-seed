package com.yupaits.msg;

import com.yupaits.commons.utils.msg.email.EmailMsg;
import com.yupaits.commons.utils.msg.email.EmailSender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class EmailSenderTest {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void testSendEmail() {
        EmailSender sender = new EmailSender(mailSender);
        EmailMsg msg = new EmailMsg().setSender(mailSender.getUsername())
                .setReceiver("ts495606653@hotmail.com")
                .setSubject("测试邮件")
                .setContent("测试邮件发送内容。");
        Assert.assertTrue(sender.send(msg));
    }
}
