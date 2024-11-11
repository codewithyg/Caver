package com.example.Caver.controller;

import com.example.Caver.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock")
    public String showStocks(Model model) {
        String stockData = stockService.getStockPrices();
        model.addAttribute("stockData", stockData);
        return "stock";
    }
}