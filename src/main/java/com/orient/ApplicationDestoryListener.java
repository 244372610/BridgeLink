package com.orient;

import com.orient.util.ConfigInfo;
import com.orient.util.MailSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sunweipeng on 2017/8/21.
 * 监听Spring容器死掉后发送邮件
 */

@Component
public class ApplicationDestoryListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextClosedEvent) {
            MailSendUtil mailSendUtil = ((ContextClosedEvent) event).getApplicationContext().getBean("mailSendUtil", MailSendUtil.class);
            mailSendUtil.send(ConfigInfo.MAIL_RECEIVER,"服务已关闭","服务因为不知名原因已经停掉");
        }
    }
}


