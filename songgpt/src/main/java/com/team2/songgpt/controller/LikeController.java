package com.team2.songgpt.controller;

import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("like")
public class LikeController {

    private final LikeService likeService;

    //게시글좋아요
    @PostMapping("/{id}")
    public ResponseDto likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updatePostLike(id, userDetails.getMember());
    }

    //게시글좋아요취소
    @DeleteMapping("/{id}")
    public ResponseDto likeCancelPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.cancelLikePost(id, userDetails.getMember());
    }

    //게시글 좋아요 목록 가져오기
    @GetMapping
    public ResponseDto<List<Long>> getLikePosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Long> postIdList = likeService.getLikePosts(userDetails.getMember());
        return ResponseDto.setSuccess("post list success", postIdList);
    }
}