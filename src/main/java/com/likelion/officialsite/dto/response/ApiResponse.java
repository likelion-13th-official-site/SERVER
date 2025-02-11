package com.likelion.officialsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;


    public ApiResponse(boolean success,int code,String message){
        this.message = message;
        this.success = success;
        this.code = code;
    }


}