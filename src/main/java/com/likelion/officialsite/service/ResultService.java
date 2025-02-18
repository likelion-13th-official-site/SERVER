package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ResultRequestDto;
import com.likelion.officialsite.dto.response.DocumentResultResponseDto;
import com.likelion.officialsite.dto.response.FinalResultResponseDto;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.exception.InvalidPasswordException;
import com.likelion.officialsite.exception.InvalidUserStatusException;
import com.likelion.officialsite.repository.ApplicationRepository;
import com.likelion.officialsite.exception.EmailValidationException;
import com.likelion.officialsite.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ApplicationRepository applicationRespository;
    private final MailService mailService;
    private final PasswordService passwordService;



    /**
     * 1차 서류 결과 조회
     */
    public DocumentResultResponseDto getDocumentResult(ResultRequestDto resultRequestDto){
       return toDocumentResultResponseDto(ValidateEmailAndPassword(resultRequestDto.getEmail(), resultRequestDto.getPassword()));
    }

    public FinalResultResponseDto getFinalResult(ResultRequestDto resultRequestDto) {
        Application validatedApplication = ValidateEmailAndPassword(resultRequestDto.getEmail(), resultRequestDto.getPassword());

        //상태 검증 ( 1차 불합자가 들어오는 것을 방지 )
        EnumSet<Status> allowedStatuess = EnumSet.of(Status.최종탈락,Status.최종합격);
        if(!allowedStatuess.contains(validatedApplication.getStatus())){
            throw new InvalidUserStatusException("최종 결과 조회가 불가한 계정입니다.");
        }

        return FinalResultResponseDto.builder()
                .name(validatedApplication.getName())
                .track(validatedApplication.getTrack())
                .status(validatedApplication.getStatus())
                .build();

    }

    //메일,비번 검증 공통 메서드
    private Application ValidateEmailAndPassword(String email,String password){
        //필수 입력 검증
        if( password == null){
            throw new IllegalArgumentException("비밀번호를 입력해야 합니다.");
        }

        mailService.validateEmail(email);

        //이메일 존재 여부 확인
        Application application = applicationRespository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 이메일 입니다"));

        //비번 검중
        if(!passwordService.checkPassword(password, application.getPassword())){
            throw  new InvalidPasswordException("비밀번호가 틀렸습니다");
        }

        return application;
    }


    /**
     * Applicant 엔티티 -> DocumentResponseDto 변환
     */
    private DocumentResultResponseDto toDocumentResultResponseDto(Application application) {
        return DocumentResultResponseDto.builder()
                .name(application.getName())
                .track(application.getTrack())
                .status(application.getStatus())
                .interviewStartTime(application.getConfirmedInterviewTime() != null ? application.getConfirmedInterviewTime().getStartTime() : null)
                .interviewEndTime(application.getConfirmedInterviewTime() != null ? application.getConfirmedInterviewTime().getEndTime() : null)
                .build();
    }

}



