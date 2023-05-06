package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TextGptResponseDto {
    @JsonProperty("usage")
    private Usage usage;
    @JsonProperty("choices")
    private List<TextChoice> choices;
    @JsonProperty("model")
    private String model;
    @JsonProperty("created")
    private LocalDate created;
    @JsonProperty("object")
    private String object;
    @JsonProperty("id")
    private String id;

    @Builder
    public TextGptResponseDto(String id, String object,
                              LocalDate created, String model,
                              List<TextChoice> choices) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }
}
