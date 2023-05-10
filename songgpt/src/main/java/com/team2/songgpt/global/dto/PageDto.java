package com.team2.songgpt.global.dto;

import com.team2.songgpt.dto.post.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageDto {
    private List<PostResponseDto.AllPostResponseDto> postResponseDtos;
    private Integer totalPage;

    public PageDto(List<PostResponseDto.AllPostResponseDto> postResponseDtos, Integer totalPage) {
        this.postResponseDtos = postResponseDtos;
        this.totalPage = totalPage;
    }
}
