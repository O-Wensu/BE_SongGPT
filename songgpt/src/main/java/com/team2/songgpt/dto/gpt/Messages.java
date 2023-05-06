package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Messages {

    @JsonProperty("content")
    private String content;
    @JsonProperty("role")
    private String role;

    public Messages(String content, String role) {
        this.content = content;
        this.role = role;
    }
}
