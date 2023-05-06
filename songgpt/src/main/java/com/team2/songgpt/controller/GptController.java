package com.team2.songgpt.controller;

import com.team2.songgpt.dto.gpt.CheckModelResponseDto;
import com.team2.songgpt.dto.gpt.GptResponseDto;
import com.team2.songgpt.dto.gpt.QuestionRequestDto;
import com.team2.songgpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-gpt")
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;
    @PostMapping("/question")
    public GptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return gptService.askQuestion(requestDto);
    }

    @GetMapping("/Model")
    public CheckModelResponseDto checkModel() {
        return gptService.checkModel();
    }
}
