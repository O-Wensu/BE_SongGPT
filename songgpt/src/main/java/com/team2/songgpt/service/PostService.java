package com.team2.songgpt.service;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public ResponseDto<?> savePost(PostRequestDto postRequestDto, Member member) {
        Post post = new Post(postRequestDto, member);
        Long postId = postRepository.save(post).getId();
        return ResponseDto.setSuccess("post create success", postId);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public ResponseDto<?> deletePost(Long id, Member member) {
        Post post = ValidateExistPost(id);
        validatePostAuthor(member, post);
        postRepository.delete(post);
        return ResponseDto.setSuccess("post delete success", null);
    }

    /**
     * 게시글들 조회
     */
    public ResponseDto<List<PostResponseDto>> getPosts(Pageable pageable, Member member) {
        List<Post> responseList = postRepository.findAll(pageable).getContent();
        List<PostResponseDto> postResponseDtoList = responseList.stream().map(PostResponseDto::new).collect(Collectors.toList());

        for (int i = 0; i < responseList.size(); i++) {
            Post post = responseList.get(i);
            int index = i;
            likeRepository.findByMemberIdAndPostId(member.getId(), post.getId()).ifPresent(like -> {
                PostResponseDto responseDto = postResponseDtoList.get(index);
                responseDto.setLikeStatus(true);
                postResponseDtoList.add(responseDto);
            });
        }

        return ResponseDto.setSuccess("find all Post success", postResponseDtoList);
    }

    /**
     * 상세 게시글 조회
     */
    public ResponseDto<PostResponseDto> getPost(Long id, Member member) {
        Post post = ValidateExistPost(id);
        PostResponseDto responseDto = new PostResponseDto(post);
        //게시글을 좋아요 했는가/안했는가 판단
        likeRepository.findByMemberIdAndPostId(member.getId(), post.getId()).ifPresent(like -> {
            responseDto.setLikeStatus(true);
        });

        return ResponseDto.setSuccess("post create success", responseDto);
    }

    // ==== 유효성 검증 ====
    private void validatePostAuthor(Member member, Post post) {
        if (!member.getNickname().equals(post.getNickname())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    private Post ValidateExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }

}
