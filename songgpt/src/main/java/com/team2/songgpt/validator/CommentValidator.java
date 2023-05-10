package com.team2.songgpt.validator;

import com.team2.songgpt.entity.Comment;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {
    private final CommentRepository commentRepository;

    public void validateCommentAuthor(Member member, Comment comment) {
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTHORIZATION.getMessage());
        }
    }

    public Comment validateExistComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_COMMENT.getMessage())
        );
    }
}
