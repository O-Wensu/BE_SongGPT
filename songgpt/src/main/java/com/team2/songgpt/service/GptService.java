package com.team2.songgpt.service;

import com.team2.songgpt.dto.gpt.GptRequestDto;
import com.team2.songgpt.dto.gpt.GptResponseDto;
import com.team2.songgpt.dto.gpt.QuestionRequestDto;
import com.team2.songgpt.global.config.GptConfig;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GptService {

    private final GptConfig gptConfig;
    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<GptRequestDto> buildHttpEntity(GptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(GptConfig.MEDIA_TYPE));
        headers.add(GptConfig.AUTHORIZATION, GptConfig.BEARER + gptConfig.getApiKey());
        return new HttpEntity<>(requestDto, headers);
    }

    public GptResponseDto getResponse(HttpEntity<GptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<GptResponseDto> responseEntity = restTemplate.postForEntity(
                GptConfig.URL,
                chatGptRequestDtoHttpEntity,
                GptResponseDto.class);

        return responseEntity.getBody();
    }

    public GptResponseDto askQuestion(QuestionRequestDto requestDto) {
        String question = genQuestion(requestDto);

        return this.getResponse(this.buildHttpEntity(
                new GptRequestDto(
                        GptConfig.MODEL,
                        question,
                        GptConfig.MAX_TOKEN,
                        GptConfig.TEMPERATURE,
                        GptConfig.TOP_P))
        );
    }

    private String genQuestion(QuestionRequestDto requestDto) {
        return requestDto.getFeelTag().toString() + " ,"
                + requestDto.getWeatherTag().toString() + " ,"
                + requestDto.getGenreTag().toString() + " ,"
                + requestDto.getRequirement()
                + "Can you recommend some songs? \nAnswer in English.";
    }
}
