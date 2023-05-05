package com.team2.songgpt.dto.like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeResponseDto {
    private boolean likeStatus;
    private int likeCount;
}
