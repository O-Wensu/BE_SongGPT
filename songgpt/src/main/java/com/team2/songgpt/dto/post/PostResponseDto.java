package com.team2.songgpt.dto.post;

import com.team2.songgpt.entity.Like;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String nickname;
    private String question;
    private String answer;
    private FeelEnum feelTag;
    private WeatherEnum weatherTag;
    private GenreEnum genreTag;
    private LocalDateTime createdAt;
    private boolean likeStatus;
    private int likeCount;

    public PostResponseDto(Post post) {
        this.nickname = post.getNickname();
        this.question = post.getQuestion();
        this.answer = post.getAnswer();
        this.feelTag = post.getFeelTag();
        this.weatherTag = post.getWeatherTag();
        this.genreTag = post.getGenreTag();
        this.createdAt = post.getCreatedAt();
        this.likeStatus = false;
        this.likeCount = post.getLikes().size();
    }

    public void setLikeStatus(boolean status) {
        this.likeStatus = status;
    }
}

