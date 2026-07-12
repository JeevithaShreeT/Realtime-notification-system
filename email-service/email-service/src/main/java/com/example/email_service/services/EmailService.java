package com.example.email_service.services;

import com.example.email_service.models.NotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${app.mail.from}")
    private String from;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    private String getSubject(String type){

        switch (type){
            case "welcome" : return "welcome to our platform";
            case "login" : return "you have logged in";
            default : return "notification";
        }
    }

    public void sendEmail(NotificationEvent event){

        try{

            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setFrom(from);
            mail.setTo(event.getEmail());
            mail.setSubject(getSubject(event.getType()));
            mail.setText(event.getMessage());

            mailSender.send(mail);

            System.out.println("Email sent to : "+event.getEmail()+" "+"type : "+event.getType());
        }
        catch(Exception e){
            System.out.println("Cannot send email : "+e.getMessage());
        }

    }
}
