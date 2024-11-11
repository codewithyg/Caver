package com.example.Caver.controller;

import com.example.Caver.service.WeatherService;
import org.springframework.ui.Model;
import com.example.Caver.model.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final WeatherService weatherService;

    // WeatherService 주입
    public HomeController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home(Model model) {
        String city = "Busan";  // 예시로 서울 날씨 정보를 가져옵니다.

        // 날씨 데이터를 WeatherService를 통해 가져오기
        Weather weatherData = weatherService.getCurrentWeather(city);

        // 날씨 데이터를 모델에 추가
        model.addAttribute("weatherData", weatherData);

        return "index";  // "index.html"로 날씨 데이터 전달
    }
}