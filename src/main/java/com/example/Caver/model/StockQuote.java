package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockQuote {
    private String symbol;
    private String name;
    private String exchange;
    private Double price;
}
