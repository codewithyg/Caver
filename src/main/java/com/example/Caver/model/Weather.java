package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

public class Weather {
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private double temperature;
    @Getter
    @Setter
    private int humidity;
    @Getter
    @Setter
    private double windSpeed;
    @Getter
    @Setter
    private double tempMin;
    @Getter
    @Setter
    private double tempMax;
    @Getter
    @Setter
    private String iconCode;

}
