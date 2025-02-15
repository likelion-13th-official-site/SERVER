package com.likelion.officialsite.controller;

import com.likelion.officialsite.dto.request.ApplicationRequestDto;
import com.likelion.officialsite.dto.request.ApplicationUpdateDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.dto.response.ApplicationResponseDto;
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
        return ResponseEntity.ok(new ApiResponse(true, 200,"지원서가 성공적으로 생성되었습니다."));
    }

    @PostMapping("/view")
    public ResponseEntity<ApiResponse> viewApplication(@RequestBody ApplicationRequestDto requestDto) {
        ApplicationResponseDto responseDto = applicationService.findApplicationByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(new ApiResponse(true, 200, "지원서 조회 성공", responseDto));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateApplication(@RequestBody ApplicationUpdateDto updateDto) {
        applicationService.updateApplication(updateDto);
        return ResponseEntity.ok(new ApiResponse(true, 200, "지원서가 성공적으로 수정되었습니다."));
    }


}
