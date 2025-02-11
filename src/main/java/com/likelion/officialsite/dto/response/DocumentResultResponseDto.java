package com.likelion.officialsite.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.officialsite.entity.InterviewTime;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import jakarta.persistence.criteria.CriteriaBuilder;
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
<<<<<<< HEAD
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime interviewStartTime;

    @JsonFormat(pattern = "MM-dd HH:mm")
=======
    private LocalDateTime interviewStartTime;
>>>>>>> ecd8332 (Refactor 서류 결과 학인 기능)
    private LocalDateTime interviewEndTime;
}
