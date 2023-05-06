package com.team2.songgpt.repository;

import com.team2.songgpt.entity.Likes;
import com.team2.songgpt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberIdAndPostId(Long memberId, Long postId);

    void deleteByMemberIdAndPostId(Long memberId, Long postId);

    void deleteByMemberId(Long member_id);


    List<Likes> findAllByMemberId(Long member_id);
}
