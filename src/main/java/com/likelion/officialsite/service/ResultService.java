package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ResultRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.dto.response.DocumentResultResponseDto;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.exception.EmailValidationException;
import com.likelion.officialsite.exception.InvalidEmailException;
import com.likelion.officialsite.exception.InvalidPasswordException;
import com.likelion.officialsite.repository.ApplicationRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ApplicationRespository applicationRespository;

    /**
     * 1차 서류 결과 조회
     */
    public DocumentResultResponseDto getDocumentResult(ResultRequestDto resultRequestDto){

        //형식 오류
        if (resultRequestDto.getEmail() == null || !resultRequestDto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new EmailValidationException("올바르지 않은 이메일 형식입니다.");
        }

        Application application = applicationRespository.findByEmail(resultRequestDto.getEmail())
                .orElse(null);

        if(application == null){ //존재하지 않는 이메일일 때
            throw  new EmailValidationException("유효하지 않는 이메일입니다");
        }else{ //존재하지만 비밀번호가 틀릴 때
            if(!application.getPassword().equals(resultRequestDto.getPassword())){
                throw  new InvalidPasswordException("비밀번호가 틀렸습니다");
            }
        }
        return toDocumentResultResponseDto(application);
    }

    /**
     * Applicant 엔티티 -> ApplicantResponseDto 변환
     */
    private DocumentResultResponseDto toDocumentResultResponseDto(Application application) {
        return DocumentResultResponseDto.builder()
                .name(application.getName())
                .track(application.getTrack())
                .status(application.getStatus())
                .confirmedInterviewTime(application.getConfirmedInterviewTime())
                .build();
    }
}



