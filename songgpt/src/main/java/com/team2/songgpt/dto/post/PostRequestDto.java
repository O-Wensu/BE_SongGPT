package com.team2.songgpt.dto.post;

import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequestDto {
    private String question;
    private String answer;
    private FeelEnum feelTag;
    private WeatherEnum weatherTag;
    private GenreEnum genreTag;
    private String requirement;
}
