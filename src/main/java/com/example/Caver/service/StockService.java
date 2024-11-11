package com.example.Caver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

    // application.properties 또는 환경 변수에서 API 키를 주입받음
    @Value("${stock.api.key}")
    private String apiKey;

    private final String STOCK_API_URL = "https://financialmodelingprep.com/api/v3/quote/AAPL,GOOGL,AMZN?apikey=";

    public String getStockPrices() {
        RestTemplate restTemplate = new RestTemplate();
        // API URL에 주입된 API 키 추가
        String urlWithApiKey = STOCK_API_URL + apiKey;
        return restTemplate.getForObject(urlWithApiKey, String.class);
    }
}