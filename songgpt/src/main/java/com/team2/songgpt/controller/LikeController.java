package com.team2.songgpt.controller;

import com.team2.songgpt.dto.like.LikeResponseDto;
import com.team2.songgpt.dto.member.SignupRequestDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Like", description = "좋아요 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("like")
public class LikeController {

    private final LikeService likeService;

    //좋아요
    @Operation(summary = "likePost", description = "게시글 좋아요",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @PostMapping("/{id}")
    public ResponseDto<LikeResponseDto> likePost(@PathVariable(name = "id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updatePostLike(id, userDetails.getMember());
    }

    //게시글 좋아요 목록 가져오기
    @Operation(summary = "Get Like Posts", description = "게시글 좋아요 목록 가져오기",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @GetMapping
    public ResponseDto<List<Long>> getLikePosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Long> postIdList = likeService.getLikePosts(userDetails.getMember());
        return ResponseDto.setSuccess(postIdList);
    }
}