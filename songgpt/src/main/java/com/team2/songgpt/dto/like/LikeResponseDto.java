package com.team2.songgpt.dto.like;

import com.team2.songgpt.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "좋아요 응답 DTO")
public class LikeResponseDto {
    private boolean likeStatus;
    private int likeCount;

    public LikeResponseDto(Post post, boolean likeStatus) {
        this.likeStatus = likeStatus;
        this.likeCount = post.getLikes().size();
    }
}
