package com.team2.songgpt.service;

import com.team2.songgpt.dto.like.LikeResponseDto;
import com.team2.songgpt.entity.Likes;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostValidator postValidator;

    /**
     * 좋아요
     */
    @Transactional

    public ResponseDto<LikeResponseDto> updatePostLike(Long id, Member member) {
        // 게시글 존재확인
        Post post = postValidator.validateExistPost(id);
        Likes postLike = isPostLike(member, post);

        //좋아요한 사람
        if (postLike != null) {
            post.getLikes().remove(postLike);
            return ResponseDto.setSuccess("like cancel", new LikeResponseDto(post, false));
        }

        //좋아요 안 한 사람
        Likes likes = new Likes(member, post);
        return ResponseDto.setSuccess("like success", new LikeResponseDto(post, true));
    }

    @Transactional
    public List<Long> getLikePosts(Member member) {
        return likeRepository.findAllByMemberId(member.getId()).stream().map(likes -> likes.getPost().getId()).collect(Collectors.toList());
    }


    // 좋아요 여부확인
    private Likes isPostLike(Member member, Post post) {
        Optional<Likes> like = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
        return like.orElse(null);
    }
}