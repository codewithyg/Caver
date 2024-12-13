package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 현재 날씨 데이터를 저장하는 DTO
 * location : 위치
 * description : 날씨 설명
 * temperature : 온도
 * humidity : 습도
 * windSpeed : 풍속
 * tempMin : 최저온도
 * tempMax : 최고온도
 * feelsLike : 체감온도
 * iconCode : 날씨 아이콘
 */

@Getter
@Setter
public class Weather {
    private String location;
    private String description;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private double tempMin;
    private double tempMax;
    private double feelsLike;
    private String iconCode;

}
