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

/**
 * 날씨 정보를 제공하는 기능을 제어하는 컨트롤러
 */

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String showWeather(@RequestParam(defaultValue = "부산") String city, Model model) {
        try {
            Weather weatherData = weatherService.getCurrentWeather(city);
            List<Forecast> forecastData = weatherService.getForecast(city);

            model.addAttribute("city", city);
            model.addAttribute("weatherData", weatherData);
            model.addAttribute("forecastData", forecastData);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "도시를 다시 검색해주세요.");
            return "redirect:/weather?error=true";
        }
        return "weather";
    }
}