package com.likelion.officialsite.exception;

public class InvalidEmailException extends  RuntimeException{

    public InvalidEmailException(String message) {
        super(message);
    }
}
