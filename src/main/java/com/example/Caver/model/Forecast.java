package com.example.Caver.model;

import lombok.Getter;
import lombok.Setter;

public class Forecast {
    @Getter @Setter
    private String dateTime;
    @Getter @Setter
    private double temperature;
    @Getter @Setter
    private double tempMin;
    @Getter @Setter
    private double tempMax;
    @Getter @Setter
    private int humidity;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String iconCode;
}