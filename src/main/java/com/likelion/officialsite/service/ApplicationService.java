package com.likelion.officialsite.service;

import com.likelion.officialsite.dto.request.ApplicationRequestDto;
import com.likelion.officialsite.entity.Application;
import com.likelion.officialsite.entity.InterviewTime;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.exception.DuplicateEmailException;
import com.likelion.officialsite.exception.InterviewTimeNotFoundException;
import com.likelion.officialsite.exception.InvalidPasswordException;
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
        Application existingApplication = applicationRepository.findByEmail(requestDto.getEmail())
                .orElse(null);

        if (existingApplication != null) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }

        // 인터뷰 가능한 시간 매핑 및 검증
        List<Long> requestedIds = requestDto.getInterviewTimes();
        List<InterviewTime> selectedTimes = interviewTimeRepository.findAllById(requestedIds);

        if (selectedTimes.size() != requestedIds.size()) {
            throw new InterviewTimeNotFoundException("요청한 인터뷰 시간 중 하나 이상을 찾을 수 없습니다.");
        }

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
