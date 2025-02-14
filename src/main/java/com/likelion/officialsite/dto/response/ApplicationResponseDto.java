package com.likelion.officialsite.dto.response;

import com.likelion.officialsite.enums.Path;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationResponseDto {
    private Long id;
    private String name;
    private String email;
    private String studentNum;
    private String major;
    private String phone;
    private Path path;
    private Track track;
    private String githubLink;
    private String portfolioLink;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private List<Long> interviewTimes;
}
