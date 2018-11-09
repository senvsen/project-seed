package com.yupaits.commons.utils.msg.email;

import com.yupaits.commons.utils.msg.BaseSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author yupaits
 * @date 2018/11/9
 */
public class EmailSender extends BaseSender<EmailMsg> {

    private final JavaMailSender mailSender;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean send(EmailMsg msg) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(msg.getSender());
        mailMessage.setTo(msg.getReceiver());
        mailMessage.setSubject(msg.getSubject());
        mailMessage.setText(msg.getContent());
        mailSender.send(mailMessage);
        return true;
    }
}
