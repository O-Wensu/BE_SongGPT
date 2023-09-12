package com.team2.songgpt.entity;

import com.team2.songgpt.dto.post.PostRequestDto;
import com.team2.songgpt.global.entity.FeelEnum;
import com.team2.songgpt.global.entity.GenreEnum;
import com.team2.songgpt.global.entity.TimeStamped;
import com.team2.songgpt.global.entity.WeatherEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false, length = 1000)
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

    @Column(nullable = false)
    private String gradient;


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
        this.question = String.valueOf(postRequestDto.getQuestion());
        this.answer = postRequestDto.getAnswer();
        this.member = member;
        this.feelTag = postRequestDto.getFeelTag();
        this.weatherTag = postRequestDto.getWeatherTag();
        this.genreTag = postRequestDto.getGenreTag();
        this.gradient = postRequestDto.getGradient();
        this.likes = new ArrayList<>();
    }

}
