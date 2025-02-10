package com.likelion.officialsite.dto.request;

import com.likelion.officialsite.enums.Path;
import com.likelion.officialsite.enums.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationRequestDto {
    private String name;
    private String email;
    private String password;
    private String studentNum;
    private String major;
    private String phone;
    private Path path;
    private Track track;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String githubLink;
    private List<Long> interviewTimes;  // InterviewTime의 ID 리스트
}
