package com.example.Caver.controller;

import com.example.Caver.model.Forecast;
import com.example.Caver.model.Weather;
import com.example.Caver.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String showWeather(@RequestParam(defaultValue = "Busan") String city, Model model) {
        Weather weatherData = weatherService.getCurrentWeather(city);
        List<Forecast> forecastData = weatherService.getFiveDayForecast(city);

        model.addAttribute("weatherData", weatherData);
        model.addAttribute("forecastData", forecastData);

        return "weather";
    }
}