package com.likelion.officialsite.controller;

import com.likelion.officialsite.dto.request.AuthRequestDto;
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

    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse> sendAuthCode(@RequestBody AuthRequestDto requestDto){
        ApiResponse response=authService.sendAuthCode(requestDto);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/verify-code")
//    public ResponseEntity<ApiResponse> verifyAuthCode(){
//
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<ApiResponse> resetPassword(){
//
//    }



}
