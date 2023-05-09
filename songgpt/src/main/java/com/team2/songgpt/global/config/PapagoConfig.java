package com.team2.songgpt.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PapagoConfig {
    @Value("${PAPAGO-TRANSLATE-KEY}")
    private String apiKey;

    @Value("${PAPAGO-TRANSLATE-ID}")
    private String apiId;

    public static final String
            CONTENT_TYPE = "application/json;";

    public static final String KEY_NAME = "X-NCP-APIGW-API-KEY";
    public static final String ID_NAME = "X-NCP-APIGW-API-KEY-ID";

    public static final String KOREAN = "ko";
    public static final String ENGLISH = "en";
    public static final String JAPANESE = "ja";

    // 기타 다른언어도 있음


    public static final String URL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";


    public String getApiKey() {
        return apiKey;
    }

    public String getApiId() {
        return apiId;
    }


}
