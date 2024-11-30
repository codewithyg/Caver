package com.example.Caver.controller;

import com.example.Caver.model.Forecast;
import com.example.Caver.model.Weather;
import com.example.Caver.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    // 입력한 도시의 현재 날씨와 5일간의 일기 예보 데이터를 가져와 뷰(Thymeleaf)에 전달
    // 유효하지 않은 도시 입력 시 에러메시지를 전달하여 뷰(Thymeleaf)에서 weathererro.js를 이용하여 에러 처리
    @GetMapping("/weather")
    public String showWeather(@RequestParam(defaultValue = "Busan") String city, Model model) {
        try {
            Weather weatherData = weatherService.getCurrentWeather(city);
            List<Forecast> forecastData = weatherService.getFiveDayForecast(city);

            model.addAttribute("weatherData", weatherData);
            model.addAttribute("forecastData", forecastData);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "도시를 다시 검색해주세요.");
            return "redirect:/weather?error=true";
        }
        return "weather";
    }
}