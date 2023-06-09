package com.team2.songgpt.dto.comment;

import com.team2.songgpt.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
