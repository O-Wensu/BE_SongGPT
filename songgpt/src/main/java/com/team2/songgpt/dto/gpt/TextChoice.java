package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TextChoice {
    @JsonProperty("finish_reason")
    private String finishReason;
    @JsonProperty("index")
    private Integer index;
    @JsonProperty("text")
    private String text;

    @Builder
    public TextChoice(String finishReason, Integer index, String text) {
        this.finishReason = finishReason;
        this.index = index;
        this.text = text;
    }
}
