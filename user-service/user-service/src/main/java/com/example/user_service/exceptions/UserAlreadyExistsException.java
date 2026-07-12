package com.example.user_service.exceptions;


public class UserAlreadyExistsException extends RuntimeException{

    private String message;

    public UserAlreadyExistsException(String message){
        super(message);
    }

}
