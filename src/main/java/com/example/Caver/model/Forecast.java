package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 일기 예보 데이터 저장하는 DTO
 */

@Getter 
@Setter
public class Forecast {
    private String date;                   // 날짜
    private double tempMin;                    // 최저온도
    private double tempMax;                    // 최고온도
    private double snow;
    private double rain;
    private String sky;
    private String iconCode;                   // 날씨 아이콘
}