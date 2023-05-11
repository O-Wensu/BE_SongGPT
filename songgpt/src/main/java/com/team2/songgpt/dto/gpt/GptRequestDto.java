package com.team2.songgpt.dto.gpt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "ChatGpt에게 대답 요청 DTO")
public class GptRequestDto implements Serializable {
    private String model;
    private List<Messages> messages;

    @Builder
    public GptRequestDto(String model, List<Messages> messages) {
        this.model = model;
        this.messages = messages;
    }
}
