package com.example.notification_service.controller;

import com.example.notification_service.entity.Notifications;
import com.example.notification_service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service){
        this.service = service;
    }

    @GetMapping("/{userid}")
    public ResponseEntity<List<Notifications>> getNotifications(@PathVariable String userid){

        List<Notifications> list = service.getNotificationsByUserId(userid);

        return ResponseEntity.ok(list);

    }


}
