package com.example.demo.entity;

import com.example.demo.enums.Path;
import com.example.demo.enums.Status;
import com.example.demo.enums.Track;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applications") // 테이블 이름 설정
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

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
    private Path path;

    @Enumerated(EnumType.STRING) // 지원 분야 ENUM
    @Column(nullable = false)
    private Track track;


    @Column(nullable = false,length = 500)
    private String question_1;

    @Column(nullable = false,length = 500)
    private String question_2;
    @Column(nullable = false,length = 500)
    private String question_3;
    @Column(nullable = false,length = 500)
    private String question_4;

    @ElementCollection
    @CollectionTable(name = "interviewTime",joinColumns = @JoinColumn(name = "entity_id"))
    @Column(nullable = false)
    private List<String> interviewTime;

    @Column(length = 255)
    private String githubLink;

    @Enumerated(EnumType.STRING) //한글 enum 사용시 필수적으로 명시
    @Column(nullable = false)
    private Status status;


}
