package com.ltfullstack.commonservice.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final Configuration config;

    public EmailService(JavaMailSender javaMailSender, Configuration config){
        this.javaMailSender = javaMailSender;
        this.config = config;
    }

    /**
     * send an email with optional HTML content and attachment
     *
     * @param to                    The recipient's email address
     * @param subject               The subject of the email
     * @param text                  The body of the email, can be HTML or plain text
     * @param isHtml                whether the email body is HTML or plain text
     * @param attachment            An optional file attachment, can be null
     */
    public void sendEmail(String to, String subject, String text, boolean isHtml, File attachment){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);

            // add attachment if provided
            if(attachment != null){
                FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                helper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
            }
            javaMailSender.send(message);
            log.info("email send successfully to {}", to);

        } catch (MessagingException ex) {
            log.info("Failed to send mail to {}", to, ex);

        }
    }
    public void sendEmailWithTemplate(String to, String subject, String template, Map<String,Object> placeholders, File attachment){
        try {
                Template t = config.getTemplate(template);
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t,placeholders);
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message,true);

                helper.setSubject(subject);
                helper.setTo(to);
                helper.setText(html,true);
                if(attachment != null){
                    FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                    helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
                }

                javaMailSender.send(message);
                log.info("Email send successful to {}", to);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Faild to send email to {}", to, e);
        }
    }
}
