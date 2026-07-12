package com.example.email_service.consumer;

import com.example.email_service.models.NotificationEvent;
import com.example.email_service.services.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class EmailConsumer {

    private final EmailService service;

    private final ObjectMapper mapper;

    public EmailConsumer(EmailService service, ObjectMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "notification-events", groupId = "email-group")
    public void consumeEmail(String message){

        try{
            System.out.println("Email service received :" +message);
            //deserialization
            NotificationEvent event = mapper.readValue(message, NotificationEvent.class);

            service.sendEmail(event);
        }
        catch (Exception e){
            System.out.println("Email is not received: "+e.getMessage());
        }
    }
}
