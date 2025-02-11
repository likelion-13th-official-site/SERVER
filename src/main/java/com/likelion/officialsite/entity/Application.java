package com.likelion.officialsite.entity;

import com.likelion.officialsite.enums.Path;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "applications")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(nullable = false, length = 1500) // UTF-8 기준 한글 500자
    private String answer1;

    @Column(nullable = false, length = 1500)
    private String answer2;

    @Column(nullable = false, length = 1500)
    private String answer3;

    @Column(nullable = false, length = 1500)
    private String answer4;

    @ManyToMany
    @JoinTable(
            name = "application_interview_time",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_time_id")
    )
    private List<InterviewTime> interviewTimes;  // 선택된 인터뷰 시간 리스트


    @Column(length = 255)
    private String githubLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmed_interview_time_id")
    private InterviewTime confirmedInterviewTime; // 확정된 인터뷰 시간

    public void updatePassword(String password){
        this.password=password;
    }


}
