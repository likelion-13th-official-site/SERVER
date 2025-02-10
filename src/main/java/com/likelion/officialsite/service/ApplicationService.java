package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ApplicationRequestDto;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.entity.InterviewTime;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.exception.DuplicateEmailException;
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

        // Password 검증, 기타 로직 추가 가능

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

}
