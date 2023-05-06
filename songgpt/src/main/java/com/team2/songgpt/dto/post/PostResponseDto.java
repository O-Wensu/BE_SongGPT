package com.team2.songgpt.dto.post;

import com.team2.songgpt.dto.comment.CommentResponseDto;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long id;
    private String nickname;
    private String question;
    private String answer;
    private FeelEnum feelTag;
    private WeatherEnum weatherTag;
    private GenreEnum genreTag;
    private String requirement;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments;
    private boolean likeStatus;
    private int likeCount;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getNickname();
        this.question = post.getQuestion();
        this.answer = post.getAnswer();
        this.feelTag = post.getFeelTag();
        this.weatherTag = post.getWeatherTag();
        this.genreTag = post.getGenreTag();
        this.requirement = post.getRequirement();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.likeStatus = false;
        this.likeCount = post.getLikes().size();
    }

    public void setLikeStatus(boolean status) {
        this.likeStatus = status;
    }
}

