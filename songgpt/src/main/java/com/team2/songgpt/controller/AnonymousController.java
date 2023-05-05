package com.team2.songgpt.controller;

import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.service.AnonymousService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("anonymous")
@RequiredArgsConstructor
public class AnonymousController {

    private final AnonymousService anonymousService;

    @GetMapping("/post")
    public ResponseDto<List<PostResponseDto>> getPosts(Pageable pageable) {
        return anonymousService.getPosts(pageable); //@RequestParam: ?page=1&size=10&sort=id,DESC
    }

    @GetMapping("/post/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id){
        return anonymousService.getPost(id);
    }
}
