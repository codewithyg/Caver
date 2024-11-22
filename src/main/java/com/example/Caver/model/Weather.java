package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
