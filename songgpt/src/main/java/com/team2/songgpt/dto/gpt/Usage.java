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
@Schema(description = "Gpt Token 설정 DTO")
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
