package com.team2.songgpt.repository;

import com.team2.songgpt.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberIdAndPostId(Long memberId, Long postId);

    void deleteByMemberIdAndPostId(Long memberId, Long postId);
}
