package com.team2.songgpt.dto.gpt;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerResponseDto {
    private String answer;

    public AnswerResponseDto(String answer) {
        this.answer = answer;
    }
}
