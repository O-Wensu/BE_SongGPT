package com.team2.songgpt.service;

import com.team2.songgpt.dto.papago.PapagoRequestDto;
import com.team2.songgpt.dto.papago.PapagoResponseDto;
import com.team2.songgpt.global.config.PapagoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PapagoService {

    private final PapagoConfig papagoConfig;

    private static RestTemplate restTemplate = new RestTemplate();

    public String textTranslation(String text, String source, String target) {
        PapagoRequestDto papagoRequestDto = PapagoRequestDto.builder()
                .source(source)
                .target(target)
                .text(text).build();

        PapagoResponseDto papagoResponseDto = this.getResponse(buildHttpEntity(papagoRequestDto));

        return papagoResponseDto.getMessage().getResult().getTranslatedtext();
    }

    private HttpEntity<PapagoRequestDto> buildHttpEntity(PapagoRequestDto papagoRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(PapagoConfig.CONTENT_TYPE));///?
        headers.add(PapagoConfig.KEY_NAME,papagoConfig.getApiKey());
        headers.add(PapagoConfig.ID_NAME,papagoConfig.getApiId());
        return new HttpEntity<>(papagoRequestDto, headers);
    }

    public PapagoResponseDto getResponse(HttpEntity<PapagoRequestDto> papagoRequestDtoHttpEntity) {
        ResponseEntity<PapagoResponseDto> responseEntity = restTemplate.postForEntity(
                PapagoConfig.URL,
                papagoRequestDtoHttpEntity,
                PapagoResponseDto.class);

        return responseEntity.getBody();
    }
}
