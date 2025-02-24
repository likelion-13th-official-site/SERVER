//package com.likelion.officialsite.service;
//
//import com.likelion.officialsite.dto.request.ResetPasswordDto;
//import com.likelion.officialsite.dto.request.SendCodeRequestDto;
//import com.likelion.officialsite.dto.request.VerifyCodeRequestDto;
//import com.likelion.officialsite.dto.response.ApiResponse;
//import com.likelion.officialsite.entity.Application;
//import com.likelion.officialsite.exception.*;
//import com.likelion.officialsite.repository.ApplicationRepository;
//import com.univcert.api.UnivCert;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.SecureRandom;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Pattern;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
////    @Value("${univcert.api-key}")
////    private String key;
////    @Value("${univcert.univ-name}")
////    private String univName;
////    private final RedisTemplate<String, String> redisTemplate;
////    private static final long EXPIRATION_MINUTES = 5; // 5분 만료
////
////    private final ApplicationRepository applicationRepository;
////
////
////
////
////    //지원서 신규 생성 - 인증 코드 전송
////    public ApiResponse sendSignupCode(SendCodeRequestDto requestDto)  {
////        String email=requestDto.getEmail();
////        validateEmail(email);
////        if(applicationRepository.existsByEmail(email)){
////            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
////        }
////        return sendCode(requestDto);
////    }
////
////    //비밀번호 재설정 - 인증 코드 전송
////    public ApiResponse sendResetCode(SendCodeRequestDto requestDto)  {
////        String email=requestDto.getEmail();
////        validateEmail(email);
////        if(!applicationRepository.existsByEmail(email)){
////            throw new UserNotFoundException("존재하지 않는 회원입니다.");
////        }
////        return sendCode(requestDto);
////    }
////
////    //이메일 검증
////    private void validateEmail(String email){
////        if(email==null){
////            throw new EmailValidationException("이메일을 입력해야 합니다.");
////        }
////
////        String emailPattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
////        if(!Pattern.matches(emailPattern,email)){
////            throw new EmailValidationException("올바르지 않은 이메일 형식입니다.");
////        }
////
////        if(!email.endsWith("@sogang.ac.kr")){
////            throw new EmailValidationException("서강대학교 이메일만 입력할 수 있습니다.");
////        }
////    }
////
////    private ApiResponse sendCode(SendCodeRequestDto requestDto){
////
////        try {
////            Map<String, Object> result = UnivCert.certify(key, requestDto.getEmail(), univName, true);
////
////            if ((boolean) result.get("success")) {
////                return new ApiResponse(true,  200,"인증 코드를 성공적으로 전송했습니다.");
////            } else {
////                String message = result.get("message") != null ? result.get("message").toString() : "인증 과정에서 오류가 발생했습니다."; //메시지가 null일 경우
////                throw new VerificationFailedException(message);
////            }
////
////        } catch (IOException e) {
////            throw new UnivCertApiException("인증 API 통신 중 오류 발생", e);
////        }
////
////    }
////
////
////    //인증코드 검증
////    public ApiResponse verifyCode(VerifyCodeRequestDto requestDto)  {
////
////        try {
////            Map<String, Object> result = UnivCert.certifyCode(key, requestDto.getEmail(), univName, requestDto.getCode());
////
////            if ((boolean) result.get("success")) {
////                UnivCert.clear(key, requestDto.getEmail()); //인증 후 목록에서 삭제
////                return new ApiResponse(true, 200,"서강대학교 학생 인증에 성공했습니다.");
////            } else {
////                String message = result.get("message") != null ? result.get("message").toString() : "인증 과정에서 오류가 발생했습니다."; //메시지가 null일 경우
////                throw new VerificationFailedException(message);
////            }
////
////        } catch (IOException e) {
////            throw new UnivCertApiException("인증 API 통신 중 오류 발생", e);
////        }
////
////    }
////
////    //비밀번호 재설정
////    public void resetPassword(ResetPasswordDto requestDto)  {
////        Application app=applicationRepository.findByEmail(requestDto.getEmail())
////                .orElseThrow(()->new UserNotFoundException("존재하지 않는 회원입니다."));
////        validatePassword(requestDto.getPassword());
////        //비밀번호 해싱 적용하기??나중에ㅎ
////        app.updatePassword(requestDto.getPassword());
////        applicationRepository.save(app);
////
////    }
////
////    private void validatePassword(String password) {
////        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()-_+=]{8,20}$";
////        if (!Pattern.matches(passwordPattern, password)) {
////            throw new InvalidPasswordException("비밀번호는 8~20자의 영문과 숫자를 포함해야 합니다.");
////        }
////    }
////
////
////
////    // 특정 이메일 인증 초기화
////    public ApiResponse clearCertificationByEmail(String email) {
////        try {
////            UnivCert.clear(key, email);
////            return new ApiResponse(true, 200, "해당 이메일의 인증 기록이 삭제되었습니다.");
////        } catch (IOException e) {
////            throw new UnivCertApiException("이메일 인증 삭제 중 오류 발생", e);
////        }
////    }
////
////    // 전체 인증된 이메일 초기화
////    public ApiResponse clearAllCertifications() {
////        try {
////            Map<String, String> requestBody = new HashMap<>();
////            requestBody.put("key", key);
////
////            Map<String, Object> response = UnivCert.clear(key);
////            boolean success = (boolean) response.get("success");
////
////            if (success) {
////                return new ApiResponse(true, 200, "모든 이메일의 인증 기록이 초기화되었습니다.");
////            } else {
////                String message = response.get("message") != null ? response.get("message").toString() : "초기화 과정에서 오류가 발생했습니다.";
////                return new ApiResponse(false, 400, message);
////            }
////        } catch (IOException e) {
////            throw new UnivCertApiException("전체 이메일 인증 삭제 중 오류 발생", e);
////        }
////    }
////
////    public ApiResponse checkEmailStatus(String email) {
////        try {
////            Map<String, Object> result = UnivCert.status(key, email);
////            boolean isVerified = (boolean) result.get("success");
////
////            if (isVerified) {
////                return new ApiResponse(true, 200, "이메일이 인증된 상태입니다.");
////            } else {
////                String message = result.get("message") != null ? result.get("message").toString() : "이메일 인증이 완료되지 않았습니다.";
////                return new ApiResponse(false, 400, message);
////            }
////        } catch (IOException e) {
////            throw new UnivCertApiException("UnivCert API 통신 중 오류 발생", e);
////        }
////    }
////
////    // 인증된 사용자 목록 조회
////    public ApiResponse getCertifiedUsers() {
////        try {
////            Map<String, Object> response = UnivCert.list(key);
////            boolean success = (boolean) response.get("success");
////
////            if (success) {
////                List<Map<String, Object>> certifiedUsers = (List<Map<String, Object>>) response.get("data");
////                return new ApiResponse(true, 200, "인증된 사용자 목록 조회 성공", certifiedUsers);
////            } else {
////                String message = response.get("message") != null ? response.get("message").toString() : "사용자 목록 조회 중 오류 발생";
////                return new ApiResponse(false, 400, message);
////            }
////        } catch (IOException e) {
////            throw new UnivCertApiException("UnivCert API 통신 중 오류 발생", e);
////        }
////    }
////
//
//}
