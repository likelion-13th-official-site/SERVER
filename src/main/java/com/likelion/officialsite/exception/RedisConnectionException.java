package com.likelion.officialsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
public class RedisConnectionException extends RuntimeException {
    public RedisConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
