package com.likelion.officialsite.dto.response;

import com.likelion.officialsite.enums.Status;
import com.likelion.officialsite.enums.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class FinalResultResponseDto {

    private Status status;
    private Track track;
    private String name;

}
