package com.team2.songgpt.dto.gpt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "질문에 대한 응답 DTO")
public class AnswerResponseDto {
    private String answer;
    public AnswerResponseDto(String answer) {
        this.answer = answer;
    }
}
