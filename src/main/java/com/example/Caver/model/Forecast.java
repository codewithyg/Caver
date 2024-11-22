package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class Forecast {
    private String dateTime;
    private double temperature;
    private double tempMin;
    private double tempMax;
    private double feelsLike;
    private int humidity;
    private String description;
    private String iconCode;
}