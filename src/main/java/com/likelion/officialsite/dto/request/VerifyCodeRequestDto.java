package com.likelion.officialsite.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeRequestDto {
    private String email;
    private String code;
}
