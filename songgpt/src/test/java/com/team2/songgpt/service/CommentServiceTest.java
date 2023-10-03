package com.team2.songgpt.service;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.entity.Comment;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.entity.StatusCode;
import com.team2.songgpt.repository.CommentRepository;
import com.team2.songgpt.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
//CommentServiceTest 추가
@SpringBootTest
@ExtendWith(SpringExtension.class) //Junit5에서 Mockito 사용 : 목 객체 생성&주입
class CommentServiceTest {
/*    @Mock //목 객체 생성
    PostRepository postRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    Member member;

    @Mock
    Post post;

    @InjectMocks //목 객체 주입
    private CommentService commentService;


    @Test
    @DisplayName("댓글 작성 성공")
    void saveComment_Success() {
        //given
        Long postId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글 내용");
        post.setId(postId);
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        //when
        ResponseDto<Long> responseDto = commentService.saveComment(postId, requestDto, member);

        //then
        Assertions.assertEquals(responseDto.getStatus(), StatusCode.OK);
    }

    @Test
    @DisplayName("댓글 작성 실패 : 없는 게시글")
    void saveComment_Fail_NotFound() {
        //given
        Long postId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글 내용");

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            commentService.saveComment(postId, requestDto, member); //saveComment에서 ValidateExistPost 호출됨
        });
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void modifyComment_Success() {
        //given
        Long cmtId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글 수정 내용");
        Comment comment = new Comment(requestDto, post, member);
        comment.setMember(member);

        when(commentRepository.findById(cmtId))
                .thenReturn(Optional.of(comment));

        //when
        ResponseDto<Long> responseDto = commentService.modifyComment(cmtId, requestDto, member);

        //then
        Assertions.assertEquals(responseDto.getStatus(), StatusCode.OK);
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteComment_Success() {
        //given
        Long cmtId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글 내용");
        Comment comment = new Comment(requestDto, post, member);
        comment.setMember(member);

        when(commentRepository.findById(cmtId))
                .thenReturn(Optional.of(comment));

        //when
        ResponseDto<?> responseDto = commentService.deleteComment(cmtId, member);

        //then
        Assertions.assertEquals(responseDto.getStatus(), StatusCode.OK);
    }*/
}