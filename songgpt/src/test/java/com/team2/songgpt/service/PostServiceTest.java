package com.team2.songgpt.service;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.WeatherEnum;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.PostRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Rollback
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 등록")
    void testSavePost() {
        //given
        Member member = registerMember("user111@naver.com", "user111!!", "user111");
        PostRequestDto postRequestDto = makePostRequestDto();

        //when
        ResponseDto<Long> responseDto = postService.savePost(postRequestDto, member);

        //then
        Assertions.assertThat(responseDto.getData()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() {
        //given
        Member member = registerMember("user111@naver.com", "user111!!", "user111");
        PostRequestDto postRequestDto = makePostRequestDto();
        Long savedId = postService.savePost(postRequestDto, member).getData();

        //when, then
        assertDoesNotThrow(() -> {
            postService.deletePost(savedId, member);
        });
    }

    @Test
    @DisplayName("회원 정보와 게시글 작성자가 다르면 에러가 발생한다")
    void testDeletePostNoAuthorizeError() {
        //given
        Member member = registerMember("user111@naver.com", "user111!!", "user111");
        PostRequestDto postRequestDto = makePostRequestDto();
        Long savedId = postService.savePost(postRequestDto, member).getData();

        Member member2 = registerMember("user222@naver.com", "user222!!", "user222");

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> postService.deletePost(savedId, member2));
    }

    @Test
    @DisplayName("삭제하려는 게시글이 없으면 에러가 발생한다")
    void testDeletePostNoExistPostError() {
        //given
        Member member = registerMember("user111@naver.com", "user111!!", "user111");

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> postService.deletePost(1L, member));
    }

    @Test
    private PostRequestDto makePostRequestDto() {
        return PostRequestDto.builder()
                .question("질문 내용")
                .answer("답변 내용")
                .feelTag(FeelEnum.SAD)
                .weatherTag(WeatherEnum.CLOUD)
                .genreTag(GenreEnum.HIPHOP)
                .gradient("#FFFFFF")
                .build();
    }

    private Member registerMember(String email, String password, String nickname) {
        Member member = new Member(email, password, nickname);
        memberRepository.save(member);
        return member;
    }
}