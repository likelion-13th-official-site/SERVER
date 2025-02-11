package com.likelion.officialsite.dto.response;

import com.likelion.officialsite.entity.InterviewTime;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class DocumentResultResponseDto {

    private Status status;
    private Track track;
    private String name;
    private LocalDateTime interviewStartTime;
    private LocalDateTime interviewEndTime;
}
