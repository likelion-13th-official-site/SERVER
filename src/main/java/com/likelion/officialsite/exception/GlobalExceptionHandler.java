package com.likelion.officialsite.exception;

import com.likelion.officialsite.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidUserStatusException.class)
    public ResponseEntity<ApiResponse> handleInvalidUserStatusException(InvalidUserStatusException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }


    @ExceptionHandler(VerificationFailedException.class)
    public ResponseEntity<ApiResponse> handleVerificationFailedException(VerificationFailedException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UnivCertApiException.class)
    public ResponseEntity<ApiResponse> handleUnivCertApiException(UnivCertApiException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "인증 API 통신 오류: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<ApiResponse> handleEmailValidationException(EmailValidationException ex){
        return buildErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<ApiResponse> handleEmailSendException(EmailSendException ex) {
        log.error("이메일 전송 오류 발생: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 중 오류가 발생했습니다.");
    }

    @ExceptionHandler(RedisConnectionException.class)
    public ResponseEntity<ApiResponse> handleRedisConnectionException(RedisConnectionException ex) {
        log.error("Redis 연결 오류 발생: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 서버와의 연결 중 오류가 발생했습니다.");
    }

    @ExceptionHandler(EmailLimitExceededException.class)
    public ResponseEntity<ApiResponse> handleEmailLimitExceededException(EmailLimitExceededException ex) {
        return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        log.error("Unhandled exception: ", ex);  // 예외 메시지 출력
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
    }


    private ResponseEntity<ApiResponse> buildErrorResponse(HttpStatus status, String message) {
        ApiResponse response = new ApiResponse(false, status.value(), message);
        return new ResponseEntity<>(response, status);
    }
}
