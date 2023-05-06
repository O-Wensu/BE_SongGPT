package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Usage {
    @JsonProperty("total_tokens")
    private int totalTokens;
    @JsonProperty("completion_tokens")
    private int completionTokens;
    @JsonProperty("prompt_tokens")
    private int promptTokens;

    public Usage(int totalTokens, int completionTokens, int promptTokens) {
        this.totalTokens = totalTokens;
        this.completionTokens = completionTokens;
        this.promptTokens = promptTokens;
    }
}
