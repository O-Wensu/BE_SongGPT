package com.team2.songgpt.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@Builder
public class CommentRequestDto {
    @Size(max = 255, message = "{comment}")
    private String content;

    @ConstructorProperties({"content"})
    public CommentRequestDto(String content) {
        this.content = content;
    }
}