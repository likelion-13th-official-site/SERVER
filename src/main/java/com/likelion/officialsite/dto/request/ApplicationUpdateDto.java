package com.likelion.officialsite.dto.request;

import com.likelion.officialsite.enums.Path;
import com.likelion.officialsite.enums.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationUpdateDto {
    private String email;  // 기존 지원서를 조회하기 위한 이메일
    private String name;
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
    private String portfolioLink;
    private List<Long> interviewTimes;
}
