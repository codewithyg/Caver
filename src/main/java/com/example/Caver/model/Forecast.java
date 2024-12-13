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
    private double temp;
    private int humidity;                      // 습도
    private double windSpeed;                  // 풍속
    private String description;                // 날씨 설명
    private String iconCode;                   // 날씨 아이콘
}