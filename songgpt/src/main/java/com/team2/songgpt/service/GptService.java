package com.team2.songgpt.service;

import com.team2.songgpt.dto.gpt.*;
import com.team2.songgpt.global.config.GptConfig;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public HttpEntity<TextGptRequestDto> buildHttpEntity(TextGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(GptConfig.MEDIA_TYPE));
        headers.add(GptConfig.AUTHORIZATION, GptConfig.BEARER + gptConfig.getApiKey());
        return new HttpEntity<>(requestDto, headers);
    }


    public Boolean checkModel(String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GptConfig.AUTHORIZATION, GptConfig.BEARER + gptConfig.getApiKey());
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<CheckModelResponseDto> responseEntity = restTemplate.exchange(
                GptConfig.MODEL_INFO_URL + model, HttpMethod.GET, httpEntity, CheckModelResponseDto.class);

        List<Permission> permissions = Objects.requireNonNull(responseEntity.getBody()).getPermission();
        if (permissions.get(0).isBlocking()) {
            return false;
        }
        return true;
    }

    public GptResponseDto getResponse(HttpEntity<GptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<GptResponseDto> responseEntity = restTemplate.postForEntity(
                GptConfig.CHAT_URL,
                chatGptRequestDtoHttpEntity,
                GptResponseDto.class);

        return responseEntity.getBody();
    }

    public TextGptResponseDto getTextResponse(HttpEntity<TextGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<TextGptResponseDto> responseEntity = restTemplate.postForEntity(
                GptConfig.TEXT_URL,
                chatGptRequestDtoHttpEntity,
                TextGptResponseDto.class);

        return responseEntity.getBody();
    }


    public ResponseDto<AnswerResponseDto> askQuestion(QuestionRequestDto requestDto) {
        //모델이 열려 있는지?
        if (!checkModel(GptConfig.CHAT_MODEL)) {
            return ResponseDto.setSuccess(new AnswerResponseDto(ExceptionMessage.CANT_USE_GPT.getMessage()));
        }

        //모델 종류 설정
        GptConfig.setMODEL(GptConfig.CHAT_MODEL);

        List<Messages> messages = new ArrayList<>();
        messages.add(new Messages(requestDto.getQuestion() + " \n" + " 어울리는 노래 5개 추천 좀 해줘.", "user")); //어울리는 노래 추천 좀 해줘 recommend 5 songs
        GptResponseDto gptResponseDto = this.getResponse(this.buildHttpEntity(new GptRequestDto(GptConfig.MODEL, messages)));
        List<Choice> choices = gptResponseDto.getChoices();
        StringBuilder answer = new StringBuilder();

        for (Choice ch : choices) {
            answer.append(ch.getMessage().getContent());
        }

        return ResponseDto.setSuccess(new AnswerResponseDto(answer.toString()));
    }

    public ResponseDto<AnswerResponseDto> askTextQuestion(QuestionRequestDto requestDto) {
        //모델이 열려 있는지?
        if (checkModel(GptConfig.TEXT_MODEL)) {
            return ResponseDto.setSuccess(new AnswerResponseDto(ExceptionMessage.CANT_USE_GPT.getMessage()));
        }
        //모델 종류 설정
        GptConfig.setMODEL(GptConfig.TEXT_MODEL);

        TextGptResponseDto gptResponseDto = this.getTextResponse(this.buildHttpEntity(
                new TextGptRequestDto(
                        GptConfig.TOP_P,
                        GptConfig.TEMPERATURE,
                        GptConfig.MAX_TOKEN,
                        requestDto.getQuestion() + " \n" +
                                "어울리는 노래 5개 추천 좀 해줘.",
                        GptConfig.MODEL)));
        List<TextChoice> choices = gptResponseDto.getChoices();
        StringBuilder answer = new StringBuilder();

        for (TextChoice ch : choices) {
            answer.append(ch.getText());
        }

        return ResponseDto.setSuccess(new AnswerResponseDto(answer.toString()));
    }
}
