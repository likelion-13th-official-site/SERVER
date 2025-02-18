package com.likelion.officialsite.exception;

public class EmailLimitExceededException extends RuntimeException {
    public EmailLimitExceededException(String message) {
        super(message);
    }
}
