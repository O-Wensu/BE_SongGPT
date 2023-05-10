package com.team2.songgpt.service;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.entity.Comment;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.repository.CommentRepository;
import com.team2.songgpt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public ResponseDto<Long> saveComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Post post = ValidateExistPost(id);
        Comment comment = new Comment(commentRequestDto, post, member);
        return ResponseDto.setSuccess(null);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public ResponseDto<Long> modifyComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = ValidateExistComment(id);
        validateCommentAuthor(member, comment);
        comment.modify(commentRequestDto);
        return ResponseDto.setSuccess(comment.getId());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ResponseDto<?> deleteComment(Long id, Member member) {
        Comment comment = ValidateExistComment(id);
        validateCommentAuthor(member, comment);
        comment.getPost().getComments().remove(comment);
        return ResponseDto.setSuccess(null);
    }

    // ==== 유효성 검증 ====

    private void validateCommentAuthor(Member member, Comment comment) {
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTHORIZATION.getMessage());
        }
    }

    private Post ValidateExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_POST.getMessage())
        );
    }

    private Comment ValidateExistComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_COMMENT.getMessage())
        );
    }
}
