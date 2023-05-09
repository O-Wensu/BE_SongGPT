package com.team2.songgpt.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @Size(max = 255, message = "{comment}")
    private String content;
}
