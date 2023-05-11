package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Schema(description = "대답에 관한 정보")
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
