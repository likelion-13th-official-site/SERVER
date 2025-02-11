package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ResultRequestDto;
import com.likelion.officialsite.dto.response.DocumentResultResponseDto;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.exception.InvalidEmailException;
import com.likelion.officialsite.exception.InvalidPasswordException;
import com.likelion.officialsite.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ApplicationRepository applicationRespository;

    /**
     * 1차 서류 결과 조회
     */
    public DocumentResultResponseDto getDocumentResult(ResultRequestDto resultRequestDto){


        Application application = applicationRespository.findByEmail(resultRequestDto.getEmail())
                .orElse(null);

        if(application == null){ //존재하지 않는 이메일일 때
            throw  new InvalidEmailException("유효하지 않는 이메일입니다");
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
                .interviewStartTime(application.getConfirmedInterviewTime().getStartTime())
                .interviewEndTime(application.getConfirmedInterviewTime().getEndTime())
                .build();
    }
}



