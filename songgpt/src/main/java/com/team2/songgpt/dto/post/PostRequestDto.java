package com.team2.songgpt.dto.post;

import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequestDto {
    @NotBlank
    @Size(max = 255, message = "{post.question}")
    private String question;
    @Size(max = 1000, message = "{post.answer}")
    private String answer;
    private FeelEnum feelTag;
    private WeatherEnum weatherTag;
    private GenreEnum genreTag;
    private String startPoint;
    private String endPoint;
}
