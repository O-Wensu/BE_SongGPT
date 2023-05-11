package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "대답에 관한 정보")
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
