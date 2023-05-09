package com.team2.songgpt.service;

import com.team2.songgpt.dto.gpt.*;
import com.team2.songgpt.global.config.GptConfig;
import com.team2.songgpt.global.config.PapagoConfig;
import com.team2.songgpt.global.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GptServiceTest {
    @Autowired
    private GptService gptService;
    @Autowired
    private GptConfig gptConfig;
    @Autowired
    private PapagoService papagoService;


//    @BeforeEach
//    public void beforeEach(){
//        gptConfig = new GptConfig();
//        gptService = new GptService(gptConfig);
//    }

    @Test
    void buildHttpEntitySuccess() {
        //given
        Messages message = new Messages("Say hi", "user");
        List<Messages> messages = new ArrayList<>();
        messages.add(message);
        GptRequestDto gptRequestDto = new GptRequestDto(GptConfig.MODEL, messages);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(GptConfig.MEDIA_TYPE));
        headers.add(GptConfig.AUTHORIZATION, GptConfig.BEARER + gptConfig.getApiKey());

        //when
        HttpEntity<GptRequestDto> httpEntity = gptService.buildHttpEntity(gptRequestDto);


        //then
        assertThat(gptRequestDto).isEqualTo(httpEntity.getBody());
        assertThat(headers).isEqualTo(httpEntity.getHeaders());
    }

    @Test
    void testBuildHttpEntitySuccess() {
        //given
        TextGptRequestDto gptRequestDto = new TextGptRequestDto(
                GptConfig.TOP_P,
                GptConfig.TEMPERATURE,
                GptConfig.MAX_TOKEN,
                "Say hi",
                GptConfig.MODEL
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(GptConfig.MEDIA_TYPE));
        headers.add(GptConfig.AUTHORIZATION, GptConfig.BEARER + gptConfig.getApiKey());

        //when
        HttpEntity<TextGptRequestDto> httpEntity = gptService.buildHttpEntity(gptRequestDto);


        //then
        assertThat(gptRequestDto).isEqualTo(httpEntity.getBody());
        assertThat(headers).isEqualTo(httpEntity.getHeaders());
    }

    @Test
    @DisplayName("모델 상태 체크")
    void checkModelSuccess() {
        //given

        //when
        Boolean aBoolean = gptService.checkModel();

        //then
        assertThat(true).isEqualTo(aBoolean);
    }

    @Test
    @DisplayName("chat 모델")
    void askQuestionSuccess() {
        //given
        String s = "나는 오늘 너무 행복해. 오늘은 화창한 날씨야. 나는 케이팝을 듣고 싶어.";

        QuestionRequestDto questionRequestDto = new QuestionRequestDto(s);



        //when
        ResponseDto<AnswerResponseDto> responseDto = gptService.askQuestion(questionRequestDto);

        String a = responseDto.getData().getAnswer();
        System.out.println(a);

        //then
        assertThat("success").isEqualTo(responseDto.getMessage());
        assertThat(AnswerResponseDto.class).isEqualTo(responseDto.getData().getClass());
    }

    @Test
    @DisplayName("text 모델")
    void askTextQuestionSuccess() {
        //given
        String s = "나는 오늘 너무 행복해. 오늘은 화창한 날씨야. 나는 케이팝을 듣고 싶어.";
        QuestionRequestDto questionRequestDto = new QuestionRequestDto(s);

        //when
        ResponseDto<AnswerResponseDto> responseDto = gptService.askTextQuestion(questionRequestDto);

        String a = responseDto.getData().getAnswer();
        System.out.println(a);

        //then
        assertThat("success").isEqualTo(responseDto.getMessage());
        assertThat(AnswerResponseDto.class).isEqualTo(responseDto.getData().getClass());
    }
}