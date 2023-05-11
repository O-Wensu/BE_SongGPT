package com.team2.songgpt.dto.gpt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Gpt에게 질문하기 DTO")
public class QuestionRequestDto implements Serializable {
    @NotBlank(message = "{gpt.question}")
    private String question;
    public QuestionRequestDto(String question) {
        this.question = question;
    }
}
