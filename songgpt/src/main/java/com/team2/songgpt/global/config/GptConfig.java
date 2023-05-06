package com.team2.songgpt.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GptConfig {
    @Value("${CHAT-GPT-KEY}")
    private String apiKey;

    public static String MODEL = "gpt-3.5-turbo";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/chat/completions";
    public static final String MODEL_INFO_URL = "https://api.openai.com/v1/models/";

    public String getApiKey() {
        return apiKey;
    }

    public static void setMODEL(String MODEL) {
        GptConfig.MODEL = MODEL;
    }
}
