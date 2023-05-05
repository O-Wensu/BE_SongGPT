package com.team2.songgpt.service;

import com.team2.songgpt.dto.like.LikeResponseDto;
import com.team2.songgpt.entity.Like;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    // 좋아요
    @Transactional
    public ResponseDto updatePostLike(Long id, Member member) {
        Post post = validatePost(id);// 게시글 존재확인.
        isPostLike(member, post);//좋아요 여부확인.

        if (isPostLike(member, post)) {
            return ResponseDto.setSuccess("이미 좋아요 했습니다.", new LikeResponseDto(post, true)); // responsedto의 data가 likeResponseDto가 됨.
        }

        Like like = new Like(member, post);
        likeRepository.save(like);
        return ResponseDto.setSuccess("좋아요 성공", new LikeResponseDto(post, true));
    }

    @Transactional
    // 좋아요 취소
    public ResponseDto cancelLikePost(Long id, Member member) {
        Post post = validatePost(id);
        isPostLike(member,post);

        if (!isPostLike(member, post)) {
            return ResponseDto.setSuccess("좋아요를 하지 않았습니다.", new LikeResponseDto(post, false));
        }

        likeRepository.deleteByMemberIdAndPostId(member.getId(), id);
        return ResponseDto.setSuccess("좋아요 취소", new LikeResponseDto(post, false));
    }

    // 게시글 여부확인
    private Post validatePost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
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