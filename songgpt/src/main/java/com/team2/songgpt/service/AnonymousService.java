package com.team2.songgpt.service;

import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnonymousService {

    private final PostRepository postRepository;

    /**
     * 게시글들 조회
     */
    public ResponseDto<List<PostResponseDto>> getPosts(Pageable pageable) {
        List<PostResponseDto> postResponseDtos = postRepository.findAll(pageable).getContent().stream().map(PostResponseDto::new).collect(Collectors.toList());

        return ResponseDto.setSuccess("find all Post success", postResponseDtos);
    }

    /**
     * 상세 게시글 조회
     */
    public ResponseDto<PostResponseDto> getPost(Long id) {
        Post post = ValidateExistPost(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return ResponseDto.setSuccess("post create success", postResponseDto);
    }

    // ==== 유효성 검증 ====

    private Post ValidateExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }
}
