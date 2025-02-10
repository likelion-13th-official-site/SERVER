package com.likelion.officialsite.repository;

import com.likelion.officialsite.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByEmail(String email);
}
