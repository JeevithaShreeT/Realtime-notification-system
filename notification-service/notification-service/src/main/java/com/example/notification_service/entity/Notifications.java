package com.example.notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    private String email;

    private String type;

    private String message;

    private String status;

    private LocalDateTime time;

    @PrePersist //automatically called before saving
    public void prePersist(){
        this.time = LocalDateTime.now();
        this.status = "Pending";
    }
}
