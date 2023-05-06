package com.team2.songgpt.dto.gpt;

import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDto implements Serializable {
    private FeelEnum feelTag;
    private WeatherEnum weatherTag;
    private GenreEnum genreTag;
    private String requirement;
}
