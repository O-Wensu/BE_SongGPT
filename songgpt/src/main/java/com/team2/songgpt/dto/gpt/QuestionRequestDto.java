package com.team2.songgpt.dto.gpt;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class QuestionRequestDto implements Serializable {
    @NotBlank(message = "{gpt.question}")
    private String question;
    public QuestionRequestDto(String question) {
        this.question = question;
    }
}
