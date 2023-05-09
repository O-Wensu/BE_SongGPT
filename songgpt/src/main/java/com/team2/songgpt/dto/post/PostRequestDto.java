package com.team2.songgpt.dto.post;

import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class PostRequestDto {
    private String question;
    private String answer;
    private FeelEnum feelTag;
    private WeatherEnum weatherTag;
    private GenreEnum genreTag;

    public PostRequestDto(String question, String answer, FeelEnum feelTag, WeatherEnum weatherTag, GenreEnum genreTag) {
        this.question = question;
        this.answer = answer;
        this.feelTag = feelTag;
        this.weatherTag = weatherTag;
        this.genreTag = genreTag;
    }
}
