package com.likelion.officialsite.controller;

import com.likelion.officialsite.dto.request.ResultRequestDto;
import com.likelion.officialsite.dto.response.ApiResponse;
import com.likelion.officialsite.dto.response.DocumentResultResponseDto;
import com.likelion.officialsite.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/document")
    public ResponseEntity<ApiResponse<DocumentResultResponseDto>> getDocumentResult(@RequestBody ResultRequestDto resultRequestDto){

        DocumentResultResponseDto documentResultResponse  = resultService.getDocumentResult(resultRequestDto);
<<<<<<< HEAD
        ApiResponse<DocumentResultResponseDto> response =  new ApiResponse<>(true,200,"서류 전형 결과 조회를 성공",documentResultResponse);
=======
        ApiResponse<DocumentResultResponseDto> response =  new ApiResponse<>(true,"200","서류 전형 결과 조회를 성공했습니다.",documentResultResponse);
>>>>>>> 588e6f7 (Feat : 서류결 결과 확인 기능)
        return ResponseEntity.ok(response);
    }

    @GetMapping("/final")
    public void getFinalResult(ResultRequestDto resultRequestDto){

    }
}
