package com.team2.songgpt.dto.gpt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerResponseDto {
    private String answer;
    public AnswerResponseDto(String answer) {
        this.answer = answer;
    }
}
