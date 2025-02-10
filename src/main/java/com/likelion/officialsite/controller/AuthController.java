package com.likelion.officialsite.controller;

import com.likelion.officialsite.dto.request.ResetPasswordDto;
import com.likelion.officialsite.dto.request.SendCodeRequestDto;
import com.likelion.officialsite.dto.request.VerifyCodeRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/send-code/signup")
    public ResponseEntity<ApiResponse> sendSignupCode(@RequestBody SendCodeRequestDto requestDto){
        ApiResponse response=authService.sendSignupCode(requestDto);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse> verifyCode(@RequestBody VerifyCodeRequestDto verifyCodeRequestDto){
        ApiResponse response=authService.verifyCode(verifyCodeRequestDto);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto ){
        authService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok(new ApiResponse(true,"비밀번호 변경 완료"));
    }



}
