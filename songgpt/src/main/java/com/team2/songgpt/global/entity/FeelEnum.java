package com.team2.songgpt.global.entity;

public enum FeelEnum {
    HAPPY("happy"),
    SAD("sad"),
    ANGER("anger"),
    ANXIETY("anxiety"),
    LOVE("love"),
    EXPECT("expect");

    private String feel;

    FeelEnum(String feel) {
        this.feel = feel;
    }
}
