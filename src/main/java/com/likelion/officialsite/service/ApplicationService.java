package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ApplicationRequestDto;
import com.likelion.officialsite.dto.response.ApplicationResponseDto;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.entity.InterviewTime;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.exception.DuplicateEmailException;
import com.likelion.officialsite.exception.InvalidPasswordException;
import com.likelion.officialsite.exception.UserNotFoundException;
import com.likelion.officialsite.repository.ApplicationRepository;
import com.likelion.officialsite.repository.InterviewTimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final InterviewTimeRepository interviewTimeRepository;

    public ApplicationService(ApplicationRepository applicationRepository, InterviewTimeRepository interviewTimeRepository) {
        this.applicationRepository = applicationRepository;
        this.interviewTimeRepository = interviewTimeRepository;
    }

    public void createApplication(ApplicationRequestDto requestDto) {
        // 이메일 중복 체크
        if (applicationRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }

        // 인터뷰 가능한 시간 매핑
        List<InterviewTime> selectedTimes = interviewTimeRepository.findAllById(requestDto.getInterviewTimes());

        Application application = Application.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .studentNum(requestDto.getStudentNum())
                .major(requestDto.getMajor())
                .phone(requestDto.getPhone())
                .path(requestDto.getPath())
                .track(requestDto.getTrack())
                .answer1(requestDto.getAnswer1())
                .answer2(requestDto.getAnswer2())
                .answer3(requestDto.getAnswer3())
                .answer4(requestDto.getAnswer4())
                .githubLink(requestDto.getGithubLink())
                .status(Status.지원함)
                .interviewTimes(selectedTimes)
                .build();

        applicationRepository.save(application);
    }

    public ApplicationResponseDto findApplicationByEmailAndPassword(String email, String password) {
        // 이메일로 지원서 조회
        Application application = applicationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 이메일로 지원서를 찾을 수 없습니다."));

        // 비밀번호 확인
        if (!application.getPassword().equals(password)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return convertToResponseDto(application);
    }

    private ApplicationResponseDto convertToResponseDto(Application application) {
        ApplicationResponseDto responseDto = new ApplicationResponseDto();
        responseDto.setId(application.getId());
        responseDto.setName(application.getName());
        responseDto.setEmail(application.getEmail());
        responseDto.setStudentNum(application.getStudentNum());
        responseDto.setMajor(application.getMajor());
        responseDto.setPhone(application.getPhone());
        responseDto.setPath(application.getPath());
        responseDto.setTrack(application.getTrack());
        responseDto.setGithubLink(application.getGithubLink());
        responseDto.setAnswer1(application.getAnswer1());
        responseDto.setAnswer2(application.getAnswer2());
        responseDto.setAnswer3(application.getAnswer3());
        responseDto.setAnswer4(application.getAnswer4());

        // 인터뷰 시간 ID 리스트로 변환
        List<Long> interviewTimeIds = application.getInterviewTimes().stream()
                .map(InterviewTime::getId)
                .toList();
        responseDto.setInterviewTimes(interviewTimeIds);

        return responseDto;
    }



}
