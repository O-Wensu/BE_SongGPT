package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class GptResponseDto implements Serializable {
    @JsonProperty("usage")
    private Usage usage;
    @JsonProperty("choices")
    private List<Choice> choices;
    @JsonProperty("created")
    private int created;
    @JsonProperty("object")
    private String object;
    @JsonProperty("id")
    private String id;

    public GptResponseDto(Usage usage, List<Choice> choices, int created, String object, String id) {
        this.usage = usage;
        this.choices = choices;
        this.created = created;
        this.object = object;
        this.id = id;
    }
}
