package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.SendCodeRequestDto;
import com.likelion.officialsite.dto.request.VerifyCodeRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.univcert.api.UnivCert;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${univcert.api-key}")
    private String key;
    private static final String univName="서강대학교";

    public ApiResponse sendAuthCode(SendCodeRequestDto sendCodeRequestDto){
        try {
            UnivCert.clear(key, sendCodeRequestDto.getEmail());  //400에러 방지
            Map<String,Object> result=UnivCert.certify(key, sendCodeRequestDto.getEmail(),univName,true);
            if((boolean)result.get("success")){
                return new ApiResponse(true,"인증 코드를 성공적으로 전송했습니다.");
            }else{
                String message=result.get("message")!=null?result.get("message").toString():"알 수 없는 오류 발생";
                return new ApiResponse(false,"인증번호 발송 실패: "+message);
            }

        } catch (IOException e) {
            throw new RuntimeException("인증번호 발송 중 오류 발생: "+e.getMessage(), e);
        }
    }


    public ApiResponse verifyAuthCode(VerifyCodeRequestDto verifyCodeRequestDto) {
        try{
            Map<String,Object> result=UnivCert.certifyCode(key,verifyCodeRequestDto.getEmail(),univName,verifyCodeRequestDto.getCode());
            if((boolean)result.get("success")){
                return new ApiResponse(true,"서강대학교 학생 인증에 성공했습니다.");

            }else{
                String message=result.get("message")!=null?result.get("message").toString():"알 수 없는 오류 발생";
                return new ApiResponse(false,"인증 실패: "+message);
            }
        } catch (IOException e) {
            throw new RuntimeException("인증번호 발송 중 오류 발생: "+e.getMessage(), e);
        }

    }

    public void resetPassword(){

    }
}
