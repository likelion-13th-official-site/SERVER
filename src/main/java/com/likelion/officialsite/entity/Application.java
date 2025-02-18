package com.likelion.officialsite.entity;

import com.likelion.officialsite.enums.Path;
import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "applications")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 생성일, 수정일 자동 관리
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

    @Column(length = 1000)
    private String portfolioLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmed_interview_time_id")
    private InterviewTime confirmedInterviewTime; // 확정된 인터뷰 시간

    @Column(length = 255)
    private String interviewLocation; // 면접 장소

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // 생성일

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정일

    public void updatePassword(String password){
        this.password=password;
    }
}
