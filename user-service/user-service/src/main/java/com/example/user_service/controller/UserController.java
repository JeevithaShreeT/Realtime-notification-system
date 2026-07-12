package com.example.user_service.controller;

import com.example.user_service.dtos.AuthResponse;
import com.example.user_service.dtos.LoginRequest;
import com.example.user_service.dtos.RegisterRequest;
import com.example.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){

        AuthResponse response = service.Register(request);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody LoginRequest request){

        AuthResponse response = service.Login(request);

        return ResponseEntity.ok(response);
    }
}
