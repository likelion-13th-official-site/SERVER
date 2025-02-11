package com.likelion.officialsite.repository;

import com.likelion.officialsite.entity.InterviewTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewTimeRepository extends JpaRepository<InterviewTime, Long> {
}
