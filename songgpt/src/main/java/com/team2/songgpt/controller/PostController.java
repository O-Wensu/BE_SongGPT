package com.team2.songgpt.controller;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.dto.comment.CommentResponseDto;
import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.dto.post.PostResponseDto;
import com.team2.songgpt.global.dto.PageDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 API")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;




    @Operation(summary = "Get posts", description = "게시글 전체조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "게시글 전체조회 실패",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    // /board?page=0&size=4&sort=createdAt,DESC 요청으로 조회
    @GetMapping("/board")
    public ResponseDto<PageDto> getPosts(Pageable pageable) {
        if (!isAuthenticated()) {
            return postService.getAllPostByAnonymous(pageable);
        }
        return postService.getAllPostByMember(pageable);
    }

    @Operation(summary = "Get post", description = "게시글 단일조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "게시글 단일조회 실패",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/board/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        if (!isAuthenticated()) {
            return postService.getPostByAnonymous(id);
        }
        return postService.getPostByMember(id);
    }

    @Operation(summary = "Save post", description = "게시글 등록(저장)",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "게시글 저장 실패",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = PostRequestDto.class)))
    @PostMapping("/post")
    public ResponseDto<?> savePost(@Valid @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.savePost(postRequestDto, userDetails.getMember());
    }

    @Operation(summary = "Delete post", description = "게시글 삭제",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "게시글 삭제 실패",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/post/{id}")
    public ResponseDto<?> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getMember());
    }

    //로그인 여부 확인
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
