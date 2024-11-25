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

@Controller
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock")
    public String showStocks(Model model) {
        List<Stock> nasdaqStocks = stockService.getNasdaqStocks();
        List<Stock> kospiStocks = stockService.getKospiStocks();
        List<Stock> cryptoCurrencies = stockService.getCryptoCurrencies();
        
        model.addAttribute("nasdaqStocks", nasdaqStocks);
        model.addAttribute("kospiStocks", kospiStocks);
        model.addAttribute("cryptoCurrencies", cryptoCurrencies);
        return "stock";
    }

    @GetMapping("/stock/search")
    public String searchStocks(@RequestParam("query") String query, Model model) {
        List<StockQuote> searchResults = stockService.searchStocksBySymbol(query);
        List<HistoricalStockData> chartData = stockService.getStockChartData(query);

        model.addAttribute("searchResults", searchResults);
        model.addAttribute("chartData", chartData);

        return "stocksearch";
    }
}
