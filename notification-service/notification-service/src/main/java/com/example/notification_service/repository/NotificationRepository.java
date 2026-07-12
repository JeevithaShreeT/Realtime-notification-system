package com.example.notification_service.repository;

import com.example.notification_service.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    List<Notifications> findByUserId(String userid);
    List<Notifications> findByEmail(String email);
}
