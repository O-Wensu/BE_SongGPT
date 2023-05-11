package com.team2.songgpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.service.CommentService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) //Junit5에서 Mockito 사용 : 목 객체 생성&주입
@AutoConfigureMockMvc
@SpringBootTest //시큐리티 때문에 @WebMvcTest 쓰기 어려움
@WithMockUser(username = "testuser", roles = "USER")
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc; //웹 API 테스트

    @MockBean//스프링빈이 아닌 목 객체 생성. 여기선 컨트롤러 기능만을 테스트 하기 위함
    CommentService commentService;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    Member member;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper(); //Text Json <-> Object


    @Test
    @DisplayName("댓글 작성 성공")
    //테스트 중 예외 발생시 실패 처리되므로, 예외처리 없이도 예외를 발생시키도록 함.
    void saveComment1() throws Exception{
        // given
        Long id = 1L;
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글 내용");

        //when & then
        mockMvc.perform(post("/comment/" + id)
                .content(objectMapper.writeValueAsString(commentRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


//        Assertions.assertEquals(responseDto.getStatus(),responseDto.getMessage());




//
//        Long id = 1L;
//        CommentRequestDto requestDto = new CommentRequestDto("content");
//        String token = "valid_token";
//        Member member = registerMember("ddubi@gmail.com", "Test111*", "ddbi");
//        UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());
//        given(commentService.saveComment(id, requestDto, userDetails.getMember()))
//                .willReturn(ResponseDto.setSuccess(null));
//
//        // when
//        mockMvc.perform(post("/comment/" + id)
//                        .header("Access_Token", "Bearer " + token)
//                        .content(objectMapper.writeValueAsString(requestDto))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());

        // then
//        verify(commentService).saveComment(id, requestDto, userDetails.getMember());
    }

//    @Test
//    @DisplayName("댓글이 255자 이상이면 작성 불가")
//    void saveComment2() {
//    }
//
//    @Test
//    @DisplayName("댓글 수정 성공")
//    void modifyComment() {
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 성공")
//    void deleteComment() {
//    }


}