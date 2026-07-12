package com.example.notification_service.service;

import com.example.notification_service.entity.Notifications;
import com.example.notification_service.models.NotificationEvent;
import com.example.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo){
        this.repo = repo;
    }

    public void processNotification(NotificationEvent event){

        Notifications notify = new Notifications();

        notify.setUserId(event.getUserid());
        notify.setEmail(event.getEmail());
        notify.setType(event.getType());
        notify.setMessage(event.getMessage());

        repo.save(notify);

        notify.setStatus("Sent");

        repo.save(notify);

        System.out.println("notification saved: "+ event.getType()+ " for "+ event.getEmail());

    }

    public List<Notifications> getNotificationsByUserId (String userid){
        return repo.findByUserId(userid);
    }

}
