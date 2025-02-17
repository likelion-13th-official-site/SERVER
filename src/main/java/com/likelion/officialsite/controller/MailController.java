package com.likelion.officialsite.controller;

import com.likelion.officialsite.dto.request.EmailRequestDto;
import com.likelion.officialsite.dto.request.ResetPasswordDto;
import com.likelion.officialsite.dto.request.SendCodeRequestDto;
import com.likelion.officialsite.dto.request.VerifyCodeRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/send-code/signup")
    public ResponseEntity<ApiResponse> sendSignupCode(@RequestBody SendCodeRequestDto requestDto){
        ApiResponse response=mailService.sendSignupCode(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-code/reset")
    public ResponseEntity<ApiResponse> sendResetCode(@RequestBody SendCodeRequestDto requestDto){
        ApiResponse response=mailService.sendResetCode(requestDto);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse> verifyCode(@RequestBody VerifyCodeRequestDto requestDto){
        ApiResponse response=mailService.verifyCode(requestDto);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordDto requestDto ){
        mailService.resetPassword(requestDto);
        return ResponseEntity.ok(new ApiResponse(true,200,"비밀번호 변경 완료"));
    }


}
