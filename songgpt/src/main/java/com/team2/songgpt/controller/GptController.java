package com.team2.songgpt.controller;

import com.team2.songgpt.dto.gpt.AnswerResponseDto;
import com.team2.songgpt.dto.gpt.CheckModelResponseDto;
import com.team2.songgpt.dto.gpt.QuestionRequestDto;
import com.team2.songgpt.dto.member.SignupRequestDto;
import com.team2.songgpt.global.config.PapagoConfig;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.service.GptService;
import com.team2.songgpt.service.PapagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Chat Gpt", description = "Gpt 연결 API")
@RestController
@RequestMapping("chat-gpt")
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;
    private final PapagoService papagoService;

    @Operation(summary = "Send Chat Question", description = "ChatGpt에게 질문하기",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = QuestionRequestDto.class)))
    @PostMapping("/question")
    public ResponseDto<AnswerResponseDto> sendQuestion(@Valid @RequestBody QuestionRequestDto requestDto) {

        return  gptService.askQuestion(requestDto);
    }

    @Operation(summary = "Send Text Question", description = "Text 완성 GPT에게 질문하기",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = QuestionRequestDto.class)))
    @PostMapping("/question/text")
    public ResponseDto<AnswerResponseDto> sendTextQuestion(@Valid @RequestBody QuestionRequestDto requestDto) {
        return gptService.askTextQuestion(requestDto);
    }

}
