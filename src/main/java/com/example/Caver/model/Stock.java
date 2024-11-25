package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stock {
    private String symbol;
    private String name;
    private String currency;
    private String stockExchange;
    private String exchangeShortName;
}
