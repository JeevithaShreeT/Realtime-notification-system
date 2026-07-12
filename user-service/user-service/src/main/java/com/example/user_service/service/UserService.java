package com.example.user_service.service;

import com.example.user_service.dtos.AuthResponse;
import com.example.user_service.dtos.LoginRequest;
import com.example.user_service.dtos.RegisterRequest;
import com.example.user_service.exceptions.UserAlreadyExistsException;
import com.example.user_service.models.NotificationEvent;
import com.example.user_service.models.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.Jwtutil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserService implements UserDetailsService {

    private final Jwtutil jwtutil;

    private final UserRepository repo;

    private final PasswordEncoder pEncoder;

    private final KafkaProducerService kafkaservice;

    public UserService(Jwtutil jwtutil, UserRepository repo, PasswordEncoder pEncoder, KafkaProducerService kafkaservice){
        this.jwtutil = jwtutil;
        this.repo = repo;
        this.pEncoder = pEncoder;
        this.kafkaservice = kafkaservice;
    }

    public AuthResponse Register(RegisterRequest request){

        if(repo.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(pEncoder.encode(request.getPassword()));

        repo.save(user);

        String token = jwtutil.generateToken(user.getEmail());

        //publishing welcome event to kafka
        NotificationEvent event = new NotificationEvent(
                user.getId().toString(),
                user.getEmail(),
                "welcome",
                "welcome to our platform, " + user.getName()
        );

        kafkaservice.sendNotification(event);

        return new AuthResponse(user.getEmail(), token);

    }

    public AuthResponse Login (LoginRequest request){

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("user not exists"));

        if (!pEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtutil.generateToken(user.getEmail());

        NotificationEvent event = new NotificationEvent(
                user.getId().toString(),
                user.getEmail(),
                "login",
                "new login detected for, "+user.getName()
        );

        kafkaservice.sendNotification(event);

        return new AuthResponse(user.getEmail(), token);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }



}
