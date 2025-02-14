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

    /**
     * 1차 서류 결과 조회
     */
    public DocumentResultResponseDto getDocumentResult(ResultRequestDto resultRequestDto){
        //필수 입력 검증
        if(resultRequestDto.getEmail() == null || resultRequestDto.getPassword() == null){
            throw new IllegalArgumentException("이메일과 비밀번호를 입력해야 합니다.");
        }
        //이메일 형식 검증
        if (!resultRequestDto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new EmailValidationException("올바르지 않은 이메일 형식입니다.");
        }
        //이메일 존재 여부 확인
        Application application = applicationRespository.findByEmail(resultRequestDto.getEmail())
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 이메일 입니다"));

        //비번 검중 ( 추후 해싱 적용 )
        if(!application.getPassword().equals(resultRequestDto.getPassword())){
            throw  new InvalidPasswordException("비밀번호가 틀렸습니다");
        }


        return toDocumentResultResponseDto(application);
    }

    public FinalResultResponseDto getFinalResult(ResultRequestDto resultRequestDto) {
        //필수 입력 검증
        if(resultRequestDto.getEmail() == null || resultRequestDto.getPassword() == null){
            throw new IllegalArgumentException("이메일과 비밀번호를 입력해야 합니다.");
        }
        //이메일 형식 검증
        if (!resultRequestDto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new EmailValidationException("올바르지 않은 이메일 형식입니다.");
        }
        //이메일 존재 여부 확인
        Application application = applicationRespository.findByEmail(resultRequestDto.getEmail())
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 이메일 입니다"));

        //비번 검중 ( 추후 해싱 적용 )
        if(!application.getPassword().equals(resultRequestDto.getPassword())){
            throw  new InvalidPasswordException("비밀번호가 틀렸습니다");
        }

        //상태 검증 ( 1차 불합자가 들어오는 것을 방지 )
        EnumSet<Status> allowedStatuess = EnumSet.of(Status.최종탈락,Status.최종합격);
        if(!allowedStatuess.contains(application.getStatus())){
            throw new InvalidUserStatusException("최종 결과 조회가 불가한 계정입니다.");
        }

        return FinalResultResponseDto.builder()
                .name(application.getName())
                .track(application.getTrack())
                .status(application.getStatus())
                .build();

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



