package com.team2.songgpt.validator;

import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {
    private final PostRepository postRepository;

    public void validatePostAuthor(Member member, Post post) {
        if (!member.getNickname().equals(post.getNickname())) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTHORIZATION.getMessage());
        }
    }

    public Post validateExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_POST.getMessage())
        );
    }
}
