package com.team2.songgpt.entity;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    public Post post;

    public Comment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void setMemberAndPost(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public void modify(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
