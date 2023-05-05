package com.team2.songgpt.dto.like;

import com.team2.songgpt.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {
    private boolean likeStatus;
    private int likeCount;

    public LikeResponseDto(Post post, boolean likeStatus) {
        this.likeStatus = likeStatus;
        this.likeCount = post.getLikes().size();
    }
}
