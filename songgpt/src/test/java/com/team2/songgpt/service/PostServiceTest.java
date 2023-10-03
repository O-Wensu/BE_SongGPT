package com.team2.songgpt.service;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.Post;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.StatusCode;
import com.team2.songgpt.global.entity.WeatherEnum;
import com.team2.songgpt.repository.LikeRepository;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.PostRepository;
import com.team2.songgpt.validator.PostValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PostValidator postValidator;

    private Member member;

    @BeforeEach
    void init() {
        member = registerMember("test123@gmail.com", "test1234!", "테스트");
    }

    @Test
    @DisplayName("게시물 저장 성공")
    public void testSavePost() {
        //given
        PostRequestDto postRequestDto = makePostRequestDto();
        Post post = makePost(postRequestDto, member);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        //when
        ResponseDto<Long> response = postService.savePost(postRequestDto, member);

        //then
        assertThat(response.getData()).isEqualTo(post.getId());
        assertThat(response.getStatus()).isEqualTo(StatusCode.OK);
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    public void testDeletePost() {
        //given
        Long postId = 1L;
        PostRequestDto postRequestDto = makePostRequestDto();
        Post post = makePost(postRequestDto, member);
        when(postValidator.validateExistPost(postId)).thenReturn(post);

        //when
        ResponseDto<?> response = postService.deletePost(postId, member);

        //then
        assertThat(response.getStatus()).isEqualTo(StatusCode.OK);
    }

    @Test
    @DisplayName("존재하지 않는 게시글 삭제 실패")
    public void testDeleteNotExistPost() {
        //given
        Long postId = 1L;
        when(postValidator.validateExistPost(postId)).thenThrow(IllegalArgumentException.class);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> {
            postService.deletePost(postId, member);
        });
    }

//    @Test
//    @DisplayName("전체 게시글 조회 성공")
//    public void testGetAllPostByMember() {
//        //given
//        PostRequestDto postRequestDto = makePostRequestDto();
//        Post post = makePost(postRequestDto, member);
////        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
////        Pageable pageable = PageRequest.of(0, 4, sort);
//        List<Post> mockedPost = mock(List.class);
//        mockedPost.add(post);
//
//        Page<Post> mockPage = new PageImpl<>(mockedPost);
//        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
////        when(likeRepository.findByMemberIdAndPostId(member.getId(), post.getId())).thenReturn()
//
//        //when
//
//        //then
//    }

/*
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
*/

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

    private Post makePost(PostRequestDto postRequestDto, Member member) {
        return Post.builder()
                .id(1L)
                .nickname(member.getNickname())
                .question(postRequestDto.getQuestion())
                .answer(postRequestDto.getAnswer())
                .feelTag(postRequestDto.getFeelTag())
                .weatherTag(postRequestDto.getWeatherTag())
                .genreTag(postRequestDto.getGenreTag())
                .gradient(postRequestDto.getGradient())
                .member(member)
                .build();
    }

    private Member registerMember(String email, String password, String nickname) {
        Member member = new Member(email, password, nickname);
//        memberRepository.save(member);
        return member;
    }
}