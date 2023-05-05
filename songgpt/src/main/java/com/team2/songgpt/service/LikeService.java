package com.team2.songgpt.service;

import com.team2.songgpt.dto.like.LikeResponseDto;
import com.team2.songgpt.entity.Like;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikeResponseDto likeResponseDto;

    // 좋아요
    @Transactional
    public ResponseEntity updatePostLike(Long id, Member member) {
        Post post = validatePost(id);// 게시글 존재확인.
        isPostLike(member, post);//좋아요여부확인.

        if (!isPostLike(member, post)) {
            post.addLike();
            Like like = new PostLike(member, post);
            likeRepository.save(like);
            ResponseDto.setSuccess("좋아요성공", likeResponseDto); // responsedto의 data가 likeResponseDto가 됨.
        }

        return new ResponseEntity(likeResponseDto, HttpStatus.OK);
    }

    // 좋아요취소
    public ResponseEntity cancelLikePost(Long id, Member member) {
        Post post = validatePost(id);
        isPostLike(member,post);

        if (isPostLike(member, post)) {
            post.cancelLike();
            likeRepository.deleteByMemberIdAndPostId(member.getId(), id);
            ResponseDto.setSuccess("좋아요 취소", likeResponseDto);
        }
        return new ResponseEntity(likeResponseDto, HttpStatus.OK);

    }

    // 게시글 여부확인
    private Post validatePost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException()
        );
    }
    // 좋아요 여부확인
    private boolean isPostLike(Member member, Post post) {
        Optional<Like> like = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
        if (like.isPresent()) {
            return true;
        }
        return false;
    }
}