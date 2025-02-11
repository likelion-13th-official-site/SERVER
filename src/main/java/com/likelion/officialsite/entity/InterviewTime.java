package com.likelion.officialsite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "interview_times")
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_time_id")
    private Long id;

    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;

    @ManyToMany(mappedBy = "interviewTimes")
    private List<Application> applications;


}
