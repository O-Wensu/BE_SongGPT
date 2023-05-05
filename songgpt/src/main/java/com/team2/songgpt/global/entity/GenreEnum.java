package com.team2.songgpt.global.entity;

public enum GenreEnum {
    K_POP("k-pop"),
    POP("pop"),
    BALLAD("ballad"),
    HIPHOP("hiphop"),
    CLASSIC("classic"),
    Nature("nature");

    private String genre;

    GenreEnum(String genre) {
        this.genre = genre;
    }
}
