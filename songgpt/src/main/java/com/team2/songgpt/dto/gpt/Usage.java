package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Usage {
    @JsonProperty("total_tokens")
    private Integer totalTokens;
    @JsonProperty("completion_tokens")
    private Integer completionTokens;
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    @Builder
    public Usage(Integer totalTokens, Integer completionTokens, Integer promptTokens) {
        this.totalTokens = totalTokens;
        this.completionTokens = completionTokens;
        this.promptTokens = promptTokens;
    }
}
