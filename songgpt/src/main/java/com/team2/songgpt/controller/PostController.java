package com.team2.songgpt.controller;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // /post?page=0&size=4&sort=createdAt,DESC 요청으로 조회
    @ResponseBody
    @GetMapping
    public ResponseDto<List<PostResponseDto>> getPosts(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPosts(pageable, userDetails.getMember());
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getPost(id, userDetails.getMember());
    }

    @PostMapping
    public ResponseDto<?> savePost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.savePost(postRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getMember());
    }
}
