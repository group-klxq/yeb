package com.xxxx.mail;

import com.xxxx.server.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailReceive {
    private static final Logger LOGGER=LoggerFactory.getLogger(MailReceive.class);
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private MailProperties mailProperties;
    @Resource
    private TemplateEngine templateEngine;
    @RabbitListener(queues = "mail.welcome")
    public void handler(Employee employee){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
            System.out.println(employee.getEmail());
            //主题
            helper.setSubject("入职欢迎邮件");
            //发送的时间
            helper.setSentDate(new Date());
            //发送的内容
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("joblevelName",employee.getJoblevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            context.setVariable("posName",employee.getPosition().getName());

            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            //发送邮件
            javaMailSender.send(mimeMessage);

            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            LOGGER.error("邮件发送失败");
        }
    }
}
