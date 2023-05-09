package com.team2.songgpt.service;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team2.songgpt.dto.post.PostResponseDto.AllPostResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public ResponseDto<?> savePost(PostRequestDto postRequestDto, Member member) {
        Post post = new Post(postRequestDto, member);
        Long postId = postRepository.save(post).getId();
        return ResponseDto.setSuccess(postId);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public ResponseDto<?> deletePost(Long id, Member member) {
        Post post = ValidateExistPost(id);
        validatePostAuthor(member, post);
        postRepository.delete(post);
        return ResponseDto.setSuccess(null);
    }

    /**
     * 회원
     * 전체 게시글 조회
     */
    public ResponseDto<List<AllPostResponseDto>> getAllPostByMember(Pageable pageable) {
        Member member = getMember();

        List<Post> responseList = postRepository.findAll(pageable).getContent();
        List<AllPostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : responseList) {
            AllPostResponseDto allpostResponseDto = new AllPostResponseDto(post);
            if (likeRepository.findByMemberIdAndPostId(member.getId(), post.getId()).isPresent()) {
                allpostResponseDto.setLikeStatus(true);
            }
            postResponseDtoList.add(allpostResponseDto);
        }

        return ResponseDto.setSuccess("member: success", postResponseDtoList);
    }

    /**
     * 비회원
     * 전체 게시글 조회
     */
    public ResponseDto<List<AllPostResponseDto>> getAllPostByAnonymous(Pageable pageable) {
        List<AllPostResponseDto> postResponseDtos = postRepository.findAll(pageable).getContent().stream().map(AllPostResponseDto::new).collect(Collectors.toList());

        return ResponseDto.setSuccess("anonymous: success", postResponseDtos);
    }

    /**
     * 회원
     * 상세 게시글 조회
     */
    public ResponseDto<PostResponseDto> getPostByMember(Long id) {
        Member member = getMember();
        Post post = ValidateExistPost(id);
        PostResponseDto responseDto = new PostResponseDto(post);

        //게시글을 좋아요 했는가/안했는가 판단
        likeRepository.findByMemberIdAndPostId(member.getId(), post.getId()).ifPresent(like -> {
            responseDto.setLikeStatus(true);
        });

        return ResponseDto.setSuccess("member: success", responseDto);
    }

    /**
     * 비회원
     * 상세 게시글 조회
     */
    public ResponseDto<PostResponseDto> getPostByAnonmous(Long id) {
        Post post = ValidateExistPost(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return ResponseDto.setSuccess("anonymous: success", postResponseDto);
    }

    private Member getMember() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).get();
        return member;
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
