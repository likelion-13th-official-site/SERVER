//package com.likelion.officialsite.controller;
//
//import com.likelion.officialsite.dto.request.EmailRequestDto;
//import com.likelion.officialsite.dto.request.ResetPasswordDto;
//import com.likelion.officialsite.dto.request.SendCodeRequestDto;
//import com.likelion.officialsite.dto.request.VerifyCodeRequestDto;
//import com.likelion.officialsite.dto.response.ApiResponse;
//import com.likelion.officialsite.service.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class AuthController {
//    private final AuthService authService;
//
//    @PostMapping("/send-code/signup")
//    public ResponseEntity<ApiResponse> sendSignupCode(@RequestBody SendCodeRequestDto requestDto){
//        ApiResponse response=authService.sendSignupCode(requestDto);
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/send-code/reset")
//    public ResponseEntity<ApiResponse> sendResetCode(@RequestBody SendCodeRequestDto requestDto){
//        ApiResponse response=authService.sendResetCode(requestDto);
//        return ResponseEntity.ok(response);
//    }
//
//
//    @PostMapping("/verify-code")
//    public ResponseEntity<ApiResponse> verifyCode(@RequestBody VerifyCodeRequestDto requestDto){
//        ApiResponse response=authService.verifyCode(requestDto);
//        return ResponseEntity.ok(response);
//
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordDto requestDto ){
//        authService.resetPassword(requestDto);
//        return ResponseEntity.ok(new ApiResponse(true,200,"비밀번호 변경 완료"));
//    }
//
//    // 특정 이메일 인증 초기화
//    @PostMapping("/clear-certification/email")
//    public ResponseEntity<ApiResponse> clearCertificationByEmail(@RequestBody EmailRequestDto requestDto) {
//        ApiResponse response = authService.clearCertificationByEmail(requestDto.getEmail());
//        return ResponseEntity.ok(response);
//    }
//
//    // 전체 인증된 이메일 초기화
//    @PostMapping("/clear-certification/all")
//    public ResponseEntity<ApiResponse> clearAllCertifications() {
//        ApiResponse response = authService.clearAllCertifications();
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/check-status")
//    public ResponseEntity<ApiResponse> checkEmailStatus(@RequestBody EmailRequestDto requestDto) {
//        ApiResponse response = authService.checkEmailStatus(requestDto.getEmail());
//        return ResponseEntity.ok(response);
//    }
//
//    // 인증된 사용자 목록 조회
//    @GetMapping("/certified-users")
//    public ResponseEntity<ApiResponse> getCertifiedUsers() {
//        ApiResponse response = authService.getCertifiedUsers();
//        return ResponseEntity.ok(response);
//    }

//}
