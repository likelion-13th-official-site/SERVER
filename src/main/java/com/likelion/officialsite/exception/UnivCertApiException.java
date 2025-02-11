package com.likelion.officialsite.exception;

//UnivCert API 호출 중 네트워크 오류 발생
public class UnivCertApiException extends RuntimeException{
    public UnivCertApiException(String message) {
        super(message);
    }

    //원본예외 포함
    public UnivCertApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
