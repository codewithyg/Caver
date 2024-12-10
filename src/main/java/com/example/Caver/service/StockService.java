package com.example.Caver.service;

import com.example.Caver.model.Stock;
import com.example.Caver.model.StockQuote;
import com.example.Caver.model.HistoricalStockData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

/**
 * 주식 데이터 API 호출 및 처리 로직 담당
 */

@Service
public class StockService {

    @Value("${stock.api.key}")
    private String apiKey;

    private final LocalDate currentDate = LocalDate.now();
    private final LocalDate date1mAgo  = currentDate.minusMonths(1);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String currentDateString = currentDate.format(formatter);
    private final String date1mAgoString = date1mAgo.format(formatter);

    private final String STOCK_SEARCH_API_URL = "https://financialmodelingprep.com/api/v3/quote/";
    private final String STOCK_CHART_API_URL = "https://financialmodelingprep.com/api/v3/historical-chart/30min/";
    private final String NASDAQ_API_URL = "https://financialmodelingprep.com/api/v3/nasdaq_constituent?";
    private final String DOWJONES_API_URL = "https://financialmodelingprep.com/api/v3/dowjones_constituent?";
    private final String CRYPTO_API_URL = "https://financialmodelingprep.com/api/v3/symbol/available-cryptocurrencies?";

    // Symbol을 검색어로 사용하여 주식 검색 결과 반환
    public List<StockQuote> searchStocksBySymbol(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(STOCK_SEARCH_API_URL + query)
                .queryParam("apikey", apiKey)
                .toUriString();
        try {
            StockQuote[] stocks = restTemplate.getForObject(url, StockQuote[].class);
            return stocks != null ? Arrays.asList(stocks) : List.of();
        } catch (HttpClientErrorException e) {
            return List.of();
        }
    }

    // Symbol의 과거 주식 데이터를 반환
    // 차트 기능을 위해 사용
    public List<HistoricalStockData> getStockChartData(String symbol) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(STOCK_CHART_API_URL + symbol)
            .queryParam("from", date1mAgoString)
            .queryParam("to", currentDateString)
            .queryParam("apikey", apiKey)
            .toUriString();
        try {
            HistoricalStockData[] chartData = restTemplate.getForObject(url, HistoricalStockData[].class);
            return chartData != null ? Arrays.asList(chartData) : List.of();
        } catch (HttpClientErrorException e) {
            return List.of();
        }
    }

    // NASDAQ 주식 목록 반환
    public List<Stock> getNasdaqStocks() {
        RestTemplate restTemplate = new RestTemplate();
        String url = NASDAQ_API_URL + "apikey=" + apiKey;
        Stock[] stocks = restTemplate.getForObject(url, Stock[].class);
        return stocks != null ? Arrays.asList(stocks) : List.of();
    }

    // DOWJONES 주식 목록 반환
    public List<Stock> getDowStocks() {
        RestTemplate restTemplate = new RestTemplate();
        String url = DOWJONES_API_URL + "apikey=" + apiKey;
        Stock[] stocks = restTemplate.getForObject(url, Stock[].class);
        return stocks != null ? Arrays.asList(stocks) : List.of();
    }

    // 암호화폐 목록 반환
    public List<Stock> getCryptoCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        String url = CRYPTO_API_URL + "apikey=" + apiKey;
        Stock[] stocks = restTemplate.getForObject(url, Stock[].class);
        return stocks != null ? Arrays.asList(stocks) : List.of();
    }
}

