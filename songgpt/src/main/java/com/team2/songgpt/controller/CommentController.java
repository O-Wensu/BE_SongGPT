package com.team2.songgpt.controller;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.dto.comment.CommentResponseDto;
import com.team2.songgpt.dto.member.SignupRequestDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "save comment", description = "댓글 등록(저장)",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "댓글 저장 실패", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = CommentRequestDto.class)))
    @PostMapping("/{id}")
    public ResponseDto<Long> saveComment(@PathVariable Long id,
                                         @Valid @RequestBody CommentRequestDto commentRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(id, commentRequestDto, userDetails.getMember());
    }

    @Operation(summary = "modify comment", description = "댓글 수정",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "댓글 수정 실패",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = CommentRequestDto.class)))
    @PutMapping("/{id}")
    public ResponseDto<Long> modifyComment(@PathVariable Long id,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(id, commentRequestDto, userDetails.getMember());
    }

    @Operation(summary = "delete comment", description = "댓글 삭제",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "댓글 삭제 실패",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getMember());
    }
}
