package com.example.Caver.controller;

import com.example.Caver.service.WeatherService;
import com.example.Caver.service.NewsService;
import com.example.Caver.service.StockService;
import org.springframework.ui.Model;
import com.example.Caver.model.Weather;
import com.example.Caver.model.News;
import com.example.Caver.model.Stock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

/**
 * index의 처리를 위한 컨트롤러
 */

@Controller
public class HomeController {
    private final WeatherService weatherService;
    private final NewsService newsService;
    private final StockService stockService;
    private final Random random = new Random();

    @Autowired
    public HomeController(NewsService newsService, WeatherService weatherService, StockService stockService) {
        this.newsService = newsService;
        this.weatherService = weatherService;
        this.stockService = stockService;
    }
    
    // 메인 페이지의 뉴스, 날씨, 주식 정보 제공
    @GetMapping("/")
    public String home(Model model) {
        String city = "부산";
        int page = random.nextInt(3) + 1;

        // 날씨
        Weather weatherData = weatherService.getCurrentWeather(city);
        model.addAttribute("city", city);
        model.addAttribute("weatherData", weatherData);
        
        // 뉴스
        List<News> newsData = newsService.getHeadLines(page);
        List<News> top3News = newsData.subList(0, Math.min(newsData.size(), 3));
        model.addAttribute("top3News", top3News);

        // 주식
        List<Stock> nasdaqStocks = stockService.getNasdaqStocks();
        model.addAttribute("nasdaqStocks", nasdaqStocks);
        
        return "index"; 
    }
}