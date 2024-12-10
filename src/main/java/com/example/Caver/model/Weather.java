package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private String temperature; // 기온
    private String humidity;    // 습도
    private String windSpeed;   // 풍속
    private String tempMax;     // 최고 기온
    private String tempMin;     // 최저 기온
}
