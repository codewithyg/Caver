package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 일기 예보 데이터 저장하는 DTO
 */

@Getter 
@Setter
public class Forecast {
    private String dateTime;                   // 날짜
    private double temperature;                // 온도
    private double tempMin;                    // 최저온도
    private double tempMax;                    // 최고온도
    private double feelsLike;                  // 체감온도
    private int humidity;                      // 습도
    private String description;                // 날씨 설명
    private String iconCode;                   // 날씨 아이콘
}