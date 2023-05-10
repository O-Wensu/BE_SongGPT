package com.team2.songgpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.songgpt.dto.member.SignupRequestDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.service.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach(){

    }

    @Test
    @DisplayName("회원가입 성공")
    void signupA() throws Exception {
        //given
        SignupRequestDto requestDto = getSignupRequestDto("rnjswo@gmail.com","!Pass123", "nickn");

        //when
        MockHttpServletRequestBuilder builder = getSignupBuilder(requestDto);

        //then
        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").isString())
                .andExpect(jsonPath("message").isString())
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패, 같은 이메일")
    void signupB() throws Exception {
        //given
        SignupRequestDto requestDto = getSignupRequestDto("rnjswo@gmail.com","!Pass123", "nickn2");

        //when
        MockHttpServletRequestBuilder builder = getSignupBuilder(requestDto);

        //then
        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").isString())
                .andExpect(jsonPath("message").isString())
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패, 같은 닉네임")
    void signupC() throws Exception {
        //given
        SignupRequestDto requestDto = getSignupRequestDto("rnjswo3@gmail.com","!Pass123", "nickn");

        //when
        MockHttpServletRequestBuilder builder = getSignupBuilder(requestDto);

        //then
        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").isString())
                .andExpect(jsonPath("message").isString())
                .andExpect(jsonPath("data").isEmpty());
    }


    @Test
    void login() {
        //given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("rnjswo@gmail.com");
        requestDto.setPassword("!Pass123");
        requestDto.setNickname("nickn");
        //when
        //then
    }
    @Test
    @Order(5)
    void getMember() {

    }

    @Test
    void logout() {
    }

    @Test
    void callNewAccessToken() {
    }


    private MockHttpServletRequestBuilder getSignupBuilder(SignupRequestDto requestDto) throws JsonProcessingException {
        return post("/member/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .characterEncoding("UTF-8");
    }

    private static SignupRequestDto getSignupRequestDto(String email, String password, String nickname) {
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(password);
        requestDto.setNickname(nickname);
        return requestDto;
    }
}