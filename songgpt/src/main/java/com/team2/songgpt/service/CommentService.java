package com.team2.songgpt.service;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.entity.Comment;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.validator.CommentValidator;
import com.team2.songgpt.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;

    /**
     * 댓글 등록
     */
    @Transactional
    public ResponseDto<Long> saveComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Post post = postValidator.validateExistPost(id);
        Comment comment = new Comment(commentRequestDto, post, member);
        return ResponseDto.setSuccess(null);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public ResponseDto<Long> modifyComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = commentValidator.validateExistComment(id);
        commentValidator.validateCommentAuthor(member, comment);
        comment.modify(commentRequestDto);
        return ResponseDto.setSuccess(comment.getId());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ResponseDto<?> deleteComment(Long id, Member member) {
        Comment comment = commentValidator.validateExistComment(id);
        commentValidator.validateCommentAuthor(member, comment);
        comment.getPost().getComments().remove(comment);
        return ResponseDto.setSuccess(null);
    }
}
