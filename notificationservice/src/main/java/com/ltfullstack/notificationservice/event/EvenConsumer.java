package com.ltfullstack.notificationservice.event;

import com.ltfullstack.commonservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EvenConsumer {

    private final EmailService emailService;

    public EvenConsumer(EmailService emailService){
        this.emailService = emailService;
    }

    @RetryableTopic(
            attempts = "4",  //  3 topic retry + 1 topic DLQ
            backoff = @Backoff(delay = 1000,multiplier = 2) ,  // multiplier  hệ số nhân  lần đầu 1 s lần 2 2s
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetriableException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message){
        log.info("Message " + message);

    }

    @DltHandler
    void consumerDLTMessage(@Payload String message){
        log.info("DLT receive message " + message);
    }

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    public void testEmail(String message){
        log.info(message);

        String template = "<h1>Test Email</h1>";

        emailService.sendEmail(message,"Test Email", template,true,null);
    }

    @KafkaListener(topics = "emailTemplate", containerFactory = "kafkaListenerContainerFactory")
    public  void emailTemplate(String message){
        log.info("Received a message " + message);
        Map<String, Object> placeholder = new HashMap<>();
        placeholder.put("name","Test Email");

        emailService.sendEmailWithTemplate(message, "Welcome to chrismas", "emailTemplate.ftl",placeholder, null);
    }

}
