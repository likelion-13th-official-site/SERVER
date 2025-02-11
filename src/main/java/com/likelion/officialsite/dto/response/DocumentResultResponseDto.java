package com.likelion.officialsite.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.likelion.officialsite.entity.InterviewTime;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class DocumentResultResponseDto {

    private Status status;
    private Track track;
    private String name;
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime interviewStartTime;

    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime interviewEndTime;
}
