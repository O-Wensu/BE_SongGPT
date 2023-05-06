package com.team2.songgpt.entity;

import com.team2.songgpt.dto.comment.CommentRequestDto;
import com.team2.songgpt.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {
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

    public Comment(CommentRequestDto commentRequestDto, Post post, Member member) {
        this.content = commentRequestDto.getContent();
        this.post = post;
        this.member = member;
        post.getComments().add(this);
    }

    public void modify(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
