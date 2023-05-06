package com.team2.songgpt.global.entity;

public enum WeatherEnum {
    SUN("sunny"),
    CLOUD("cloudy"),
    RAIN("rainy"),
    SNOW("snowy"),
    STORM("windy");

    private String weather;

    WeatherEnum(String weather) {
        this.weather = weather;
    }
}
