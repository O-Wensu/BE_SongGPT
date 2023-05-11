package com.team2.songgpt.service;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.dto.post.PostResponseDto.AllPostResponseDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.PageDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.PostRepository;
import com.team2.songgpt.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PostValidator postValidator;

    /**
     * 게시글 저장
     */
    @Transactional
    public ResponseDto<Long> savePost(PostRequestDto postRequestDto, Member member) {
        Post post = new Post(postRequestDto, member);
        Long postId = postRepository.save(post).getId();
        return ResponseDto.setSuccess(postId);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public ResponseDto<?> deletePost(Long id, Member member) {
        Post post = postValidator.validateExistPost(id);
        postValidator.validatePostAuthor(member, post);
        postRepository.delete(post);
        return ResponseDto.setSuccess(null);
    }

    /**
     * 회원
     * 전체 게시글 조회
     */
    public ResponseDto<PageDto> getAllPostByMember(Pageable pageable) {
        Member member = getMember();
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> responseList = posts.getContent();
        List<AllPostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : responseList) {
            AllPostResponseDto allpostResponseDto = new AllPostResponseDto(post);
            if (likeRepository.findByMemberIdAndPostId(member.getId(), post.getId()).isPresent()) {
                allpostResponseDto.setLikeStatus(true);
            }
            postResponseDtoList.add(allpostResponseDto);
        }

        PageDto pageDto = new PageDto(postResponseDtoList, posts.getTotalPages());

        return ResponseDto.setSuccess("member: success", pageDto);
    }

    /**
     * 비회원
     * 전체 게시글 조회
     */
    public ResponseDto<PageDto> getAllPostByAnonymous(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<AllPostResponseDto> postResponseDtos = posts.getContent().stream().map(AllPostResponseDto::new).collect(Collectors.toList());
        PageDto pageDto = new PageDto(postResponseDtos, posts.getTotalPages());
        return ResponseDto.setSuccess("anonymous: success", pageDto);
    }

    /**
     * 회원
     * 상세 게시글 조회
     */
    public ResponseDto<PostResponseDto> getPostByMember(Long id) {
        Member member = getMember();
        Post post = postValidator.validateExistPost(id);
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
    public ResponseDto<PostResponseDto> getPostByAnonymous(Long id) {
        Post post = postValidator.validateExistPost(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return ResponseDto.setSuccess("anonymous: success", postResponseDto);
    }

    /**
     * 비회원
     * 전체 게시글 조회
     */
    public ResponseDto<List<AllPostResponseDto>> getPostListByAnonymous() {
        List<AllPostResponseDto> postResponseDtos = postRepository.findAll().stream().map(AllPostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("anonymous: success", postResponseDtos);
    }

    /**
     * 회원
     * 전체 게시글 조회
     */
    public ResponseDto<List<AllPostResponseDto>> getPostListByMember() {
        Member member = getMember();
        List<Post> responseList = postRepository.findAll();
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


    private Member getMember() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).get();
        return member;
    }
}
