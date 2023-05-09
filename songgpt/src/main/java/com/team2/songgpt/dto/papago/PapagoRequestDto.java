package com.team2.songgpt.dto.papago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PapagoRequestDto {

    @JsonProperty("text")
    private String text;

    @JsonProperty("target")
    private String target;

    @JsonProperty("source")
    private String source;

    @Builder
    public PapagoRequestDto(String text, String target, String source) {
        this.text = text;
        this.target = target;
        this.source = source;
    }
}
