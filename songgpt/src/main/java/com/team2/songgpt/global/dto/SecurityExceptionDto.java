package com.team2.songgpt.global.dto;

import com.team2.songgpt.global.entity.StatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecurityExceptionDto {

    private StatusCode statusCode;
    private String message;

    public SecurityExceptionDto(StatusCode statusCode, String msg) {
        this.statusCode = statusCode;
        this.message = msg;
    }
}