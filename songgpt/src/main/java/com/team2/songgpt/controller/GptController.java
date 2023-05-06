package com.team2.songgpt.controller;

import com.team2.songgpt.dto.gpt.GptResponseDto;
import com.team2.songgpt.dto.gpt.QuestionRequestDto;
import com.team2.songgpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;
    @PostMapping("/question")
    public GptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return gptService.askQuestion(requestDto);
    }

}
