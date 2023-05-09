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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    void savePost() {
        //given
        Member member = new Member("rnjswo1@gmail.com", "nickn", "!Pass123");
        memberRepository.save(member);
//        PostRequestDto postRequestDto  = new PostRequestDto("question5", "answer",  FeelEnum.HAPPY, WeatherEnum.SUN, GenreEnum.K_POP);

        //when
//        Long id = postService.savePost(postRequestDto, member).getData();
//        Post post = postRepository.findById(id).orElseThrow();

        //then
//        assertThat(post.getMember()).isEqualTo(member);
//        assertThat(post.getQuestion()).isEqualTo(postRequestDto.getQuestion());
//        assertThat(post.getAnswer()).isEqualTo(postRequestDto.getAnswer());
//        assertThat(post.getGenreTag()).isEqualTo(postRequestDto.getGenreTag());
//        assertThat(post.getFeelTag()).isEqualTo(postRequestDto.getFeelTag());
//        assertThat(post.getWeatherTag()).isEqualTo(postRequestDto.getWeatherTag());
    }

    @Test
    void deletePost() {
    }

    @Test
    void getAllPostByMember() {
    }

    @Test
    void getAllPostByAnonymous() {
    }

    @Test
    void getPostByMember() {
    }

    @Test
    void getPostByAnonmous() {
    }
}