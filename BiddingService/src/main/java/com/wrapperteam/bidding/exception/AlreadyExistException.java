package com.wrapperteam.bidding.exception;

public class AlreadyExistException extends RuntimeException{

    String errorMessage;

    public AlreadyExistException(String errorMessage) {
        this.errorMessage = errorMessage;

    }


}
