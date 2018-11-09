package com.yupaits.msg;

import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.entity.Notification;
import com.yupaits.web.mq.MsgSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class MqTest {

    @Autowired
    private MsgSender msgSender;

    @Test
    public void testSendNotification() {
        msgSender.send(new Notification().setOperator(111L));
    }

    @Test
    public void testSendMessage() {
        msgSender.send(new MessageUser().setUserId(111L).setSendAt(LocalDateTime.now()));
    }
}
