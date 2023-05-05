package com.team2.songgpt.repository;

import com.team2.songgpt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
