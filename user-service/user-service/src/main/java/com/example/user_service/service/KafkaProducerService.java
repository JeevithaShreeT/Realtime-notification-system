package com.example.user_service.service;

import com.example.user_service.models.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper mapper;

    private static final String TOPIC = "notification-events";

    public KafkaProducerService(KafkaTemplate<String, String> template, ObjectMapper mapper){
        this.template = template;
        this.mapper = mapper;
    }

    public void sendNotification(NotificationEvent event){

        try{
            //serialising
            //converts to json string
            String message = mapper.writeValueAsString(event);

            //send to kafka
            template.send(TOPIC, message);
            System.out.println("Event sent to kafka: "+message);
        }
        catch(Exception e){
            System.out.println("failed to send kafka: "+ e.getMessage());
        }
    }
}
