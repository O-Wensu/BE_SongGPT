package com.team2.songgpt.dto.gpt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class GptRequestDto implements Serializable {
    private String model;
    private List<Messages> messages;

    @Builder
    public GptRequestDto(String model, List<Messages> messages) {
        this.model = model;
        this.messages = messages;
    }
}
