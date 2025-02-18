package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ResetPasswordDto;
import com.likelion.officialsite.dto.request.SendCodeRequestDto;
import com.likelion.officialsite.dto.request.VerifyCodeRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.entity.RedisUtil;
import com.likelion.officialsite.exception.*;
import com.likelion.officialsite.repository.ApplicationRepository;
import com.univcert.api.UnivCert;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.regex.Pattern;


import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
@RequiredArgsConstructor
public class MailService {

    private static final String sender = "likelion.sgu@gmail.com"; //보내는 사람
    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;

    private final SpringTemplateEngine templateEngine;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    private final ApplicationRepository applicationRepository;
    private final PasswordService passwordService;

    // 인증번호 생성 (영문+숫자 조합)
    public String createCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }


    //메일 양식
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        String authCode=createCode(); //인증 코드 생성

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject("서강대학교 멋쟁이사자처럼 13기 인증 번호"); //제목
        message.setFrom(sender);
        message.setText(setContext(authCode), "utf-8", "html");

        //redis 인증코드 유효시간 설정
        redisUtil.setDataExpire(email, authCode, 60 * 30L);
        return message;
    }

    public void sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        if(redisUtil.existData(email)){
            redisUtil.deleteData(email);
        }

        // 이메일 요청 횟수를 Redis에서 관리 (60초 동안 3회까지 허용)
        String key = "email_limit_" + email;
        int limit = 3; // 허용되는 최대 요청 횟수
        long timeWindow = 60; // 제한 시간 (초 단위)

        if (redisUtil.existData(key)) {
            int count = Integer.parseInt(redisUtil.getData(key));
            if (count >= limit) {
                throw new EmailLimitExceededException("너무 많은 이메일 요청이 발생했습니다. 잠시 후 다시 시도하세요.");
            }
            redisUtil.setDataExpire(key, String.valueOf(count + 1), timeWindow);
        } else {
            redisUtil.setDataExpire(key, "1", timeWindow);
        }

        try {
            MimeMessage emailForm = createEmailForm(email);
            emailSender.send(emailForm);
        } catch (MessagingException e) {
            throw new EmailSendException("이메일 전송 중 오류 발생", e);
        }
    }

    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

    //지원서 신규 생성 - 인증 코드 전송
    public ApiResponse sendSignupCode(SendCodeRequestDto requestDto)  {
        String email=requestDto.getEmail();
        validateEmail(email);
        if(applicationRepository.existsByEmail(email)){
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        return sendCode(requestDto);
    }

    //비밀번호 재설정 - 인증 코드 전송
    public ApiResponse sendResetCode(SendCodeRequestDto requestDto)  {
        String email=requestDto.getEmail();
        validateEmail(email);
        if(!applicationRepository.existsByEmail(email)){
            throw new UserNotFoundException("존재하지 않는 회원입니다.");
        }
        return sendCode(requestDto);
    }

    private ApiResponse sendCode(SendCodeRequestDto requestDto){

        try {
            sendEmail(requestDto.getEmail());
            return new ApiResponse(true,  200,"인증 코드를 성공적으로 전송했습니다.");

        } catch (IOException | MessagingException e) {
            throw new UnivCertApiException("인증 API 통신 중 오류 발생", e);
        }

    }

    //이메일 검증
    public void validateEmail(String email){
        if(email==null){
            throw new EmailValidationException("이메일을 입력해야 합니다.");
        }

        String emailPattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(!Pattern.matches(emailPattern,email)){
            throw new EmailValidationException("올바르지 않은 이메일 형식입니다.");
        }

//        if(!email.endsWith("@sogang.ac.kr")){
//            throw new EmailValidationException("서강대학교 이메일만 입력할 수 있습니다.");
//        }
    }

    //인증코드 검증
    public ApiResponse verifyCode(VerifyCodeRequestDto requestDto) {
        String foundCode = redisUtil.getData(requestDto.getEmail());

        if (foundCode == null) {  // Redis에 값이 없으면 예외 발생
            throw new VerificationFailedException("인증 코드가 만료되었거나 존재하지 않습니다.");
        }

        if (!foundCode.equals(requestDto.getCode())) {  // 코드 불일치 예외 발생
            throw new VerificationFailedException("인증번호가 일치하지 않습니다.");
        }

        return new ApiResponse(true, 200, "인증 성공");
    }


    //비밀번호 재설정
    public void resetPassword(ResetPasswordDto requestDto) {
        Application app = applicationRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        if (passwordService.verifyPassword(requestDto.getPassword(), app.getPassword())) {
            throw new InvalidPasswordException("이전 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
        }

        app.updatePassword(passwordService.hashPassword(requestDto.getPassword()));
        applicationRepository.save(app);
    }


    private void validatePassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()-_+=]{8,20}$";
        if (!Pattern.matches(passwordPattern, password)) {
            throw new InvalidPasswordException("비밀번호는 8~20자의 영문과 숫자를 포함해야 합니다.");
        }
    }




}
