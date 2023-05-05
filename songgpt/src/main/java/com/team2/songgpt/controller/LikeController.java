package com.team2.songgpt.controller;

import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    //게시글좋아요
    @PostMapping("/like/{id}")
    public ResponseEntity likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likeService.updatePostLike(id, userDetails.getMember());
    }

    //게시글좋아요취소
    @DeleteMapping("/like/{id}")
    public ResponseEntity likeCancelPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.cancelLikePost(id, userDetails.getMember());
    }
}