package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Choice {
    @JsonProperty("finish_reason")
    private String finishReason;
    @JsonProperty("message")
    private Messages message;
    @JsonProperty("index")
    private int index;

    public Choice(String finishReason, Messages message, int index) {
        this.finishReason = finishReason;
        this.message = message;
        this.index = index;
    }
}
