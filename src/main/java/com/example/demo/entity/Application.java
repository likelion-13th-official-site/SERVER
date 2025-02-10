package com.example.demo.entity;

import com.example.demo.enums.Path;
import com.example.demo.enums.Status;
import com.example.demo.enums.Track;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 8)
    private String studentNum;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false , length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Path path; //지원 경로

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Track track;

    @Column(nullable = false, length = 500)
    private String question_1;

    @Column(nullable = false, length = 500)
    private String question_2;

    @Column(nullable = false, length = 500)
    private String question_3;

    @Column(nullable = false, length = 500)
    private String question_4;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private List<InterviewTime> interviewTimes;

    @Column(length = 255)
    private String githubLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmed_interview_time_id")
    private InterviewTime confirmedInterviewTime; // 확정된 인터뷰 시간

}
