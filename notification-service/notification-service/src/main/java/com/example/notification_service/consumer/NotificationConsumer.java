package com.example.notification_service.consumer;

import com.example.notification_service.models.NotificationEvent;
import com.example.notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class NotificationConsumer {

    private final NotificationService service;
    private final ObjectMapper mapper;

    public NotificationConsumer(NotificationService service, ObjectMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "notification-events", groupId = "notification-group")
    public void consumeNotification(String message){

        try{
            System.out.println("Received from kafka: "+ message);

            //deserialising
            NotificationEvent event = mapper.readValue(message, NotificationEvent.class);

            service.processNotification(event);
        }
        catch (Exception e){
            System.out.println("Error Processing notification: "+ e.getMessage());
        }

    }
}
