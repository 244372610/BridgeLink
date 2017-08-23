package com.orient.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * 邮件发送
 */
@Component
public class MailSendUtil {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    public void send(String to, String subject,String content) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(to);
        msg.setText(content);
        msg.setSubject(subject);
        this.mailSender.send(msg);
    }
}
