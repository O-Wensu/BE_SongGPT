package com.team2.songgpt.dto.gpt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class QuestionRequestDto implements Serializable {
    private String question;

    public QuestionRequestDto(String question) {
        this.question = question;
    }
}
