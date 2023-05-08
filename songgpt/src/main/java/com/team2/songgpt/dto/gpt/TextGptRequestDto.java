package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TextGptRequestDto {

    @JsonProperty("top_p")
    private Double topP;
    @JsonProperty("temperature")
    private Double temperature;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("model")
    private String model;

    @Builder
    public TextGptRequestDto(Double topP, Double temperature, Integer maxTokens, String prompt, String model) {
        this.topP = topP;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.prompt = prompt;
        this.model = model;
    }
}
