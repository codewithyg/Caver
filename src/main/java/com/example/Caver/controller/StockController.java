package com.example.Caver.controller;

import com.example.Caver.model.Stock;
import com.example.Caver.model.StockQuote;
import com.example.Caver.model.HistoricalStockData;
import com.example.Caver.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 주식 정보를 제공하는 기능을 제어하는 컨트롤러
 */

@Controller
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // NASDAQ, DOWJONES, 암호화폐 데이터를 가져와 뷰(Thymeleaf)에 전달
    @GetMapping("/stock")
    public String showStocks(Model model) {
        List<Stock> nasdaqStocks = stockService.getNasdaqStocks();
        List<Stock> dowStocks = stockService.getDowStocks();
        List<Stock> cryptoCurrencies = stockService.getCryptoCurrencies();
        
        model.addAttribute("nasdaqStocks", nasdaqStocks);
        model.addAttribute("dowStocks", dowStocks);
        model.addAttribute("cryptoCurrencies", cryptoCurrencies);
        return "stock";
    }

    // Symbol 검색 결과와 차트 데이터를 뷰(Thymeleaf)에 전달
    @GetMapping("/stock/search")
    public String searchStocks(@RequestParam("query") String query, Model model) {
        List<StockQuote> searchResults = stockService.searchStocksBySymbol(query);
        List<HistoricalStockData> chartData = stockService.getStockChartData(query);

        model.addAttribute("searchResults", searchResults);
        model.addAttribute("chartData", chartData);

        return "stocksearch";
    }
}
