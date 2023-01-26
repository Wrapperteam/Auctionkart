package com.ust.userMicroservice.userMicroservice.exception;

public class UserNotFoundException extends RuntimeException{

    String errorMessage;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
