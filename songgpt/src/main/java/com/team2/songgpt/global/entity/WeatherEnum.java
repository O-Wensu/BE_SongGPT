package com.team2.songgpt.global.entity;

public enum WeatherEnum {
    SUN("sun"),
    CLOUD("cloud"),
    RAIN("rain"),
    SNOW("snow"),
    STORM("storm");

    private String weather;

    WeatherEnum(String weather) {
        this.weather = weather;
    }
}
