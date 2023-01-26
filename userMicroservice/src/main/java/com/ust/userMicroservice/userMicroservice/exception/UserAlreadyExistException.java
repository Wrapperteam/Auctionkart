package com.ust.userMicroservice.userMicroservice.exception;

public class UserAlreadyExistException extends RuntimeException{

    String errorMessage;

    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
