package com.team2.songgpt.entity;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.TimeStamped;
import com.team2.songgpt.global.entity.WeatherEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeelEnum feelTag;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeatherEnum weatherTag;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenreEnum genreTag;

    private String requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("createdAt desc")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @Builder
    public Post(PostRequestDto postRequestDto, Member member) {
        this.nickname = member.getNickname();
        this.question = postRequestDto.getQuestion();
        this.answer = postRequestDto.getAnswer();
        this.member = member;
        this.feelTag = postRequestDto.getFeelTag();
        this.weatherTag = postRequestDto.getWeatherTag();
        this.genreTag = postRequestDto.getGenreTag();
        this.requirement = postRequestDto.getRequirement();
        this.likes = new ArrayList<>();
    }

}
