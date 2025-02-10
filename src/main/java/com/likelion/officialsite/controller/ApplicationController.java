package com.likelion.officialsite.controller;

import com.likelion.officialsite.dto.request.ApplicationRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createApplication(@RequestBody ApplicationRequestDto requestDto) {
        applicationService.createApplication(requestDto);
        return ResponseEntity.ok(new ApiResponse(true, "지원서가 성공적으로 생성되었습니다."));
    }
}
