package com.likelion.officialsite.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //success
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공했습니다."),
    CREATED(true, HttpStatus.CREATED.value(), "생성에 성공했습니다."),
    ;


    private final Boolean success;
    private final int code;
    private final String message;

    ErrorCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
