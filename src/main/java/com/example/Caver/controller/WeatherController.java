package com.example.Caver.controller;

import com.example.Caver.model.Weather;
import com.example.Caver.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // /weather 경로에서 날씨 검색
    @GetMapping("/weather")
    public String getWeather(@RequestParam(defaultValue = "부산") String city, Model model) {
        try {
            Weather weather = weatherService.getWeather(city);
            model.addAttribute("city", city);
            model.addAttribute("weatherData", weather);
            return "weather"; // weather.html로 결과 전달
        } catch (Exception e) {
            model.addAttribute("errorMessage", "날씨 정보를 가져오는 데 문제가 발생했습니다.");
            return "weather"; // 오류 메시지 전달
        }
    }
}
