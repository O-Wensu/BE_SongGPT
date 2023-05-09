package com.team2.songgpt.controller;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.team2.songgpt.dto.post.PostResponseDto.AllPostResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // /board?page=0&size=4&sort=createdAt,DESC 요청으로 조회
    @GetMapping("/board")
    public ResponseDto<List<AllPostResponseDto>> getPosts(Pageable pageable) {
        if (!isAuthenticated()) {
            return postService.getAllPostByAnonymous(pageable);
        }
        return postService.getAllPostByMember(pageable);
    }

    @GetMapping("/board/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        if (!isAuthenticated()) {
            return postService.getPostByAnonmous(id);
        }
        return postService.getPostByMember(id);
    }

    @PostMapping("/post")
    public ResponseDto<?> savePost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.savePost(postRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/post/{id}")
    public ResponseDto<?> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getMember());
    }

    //로그인 여부 확인
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
