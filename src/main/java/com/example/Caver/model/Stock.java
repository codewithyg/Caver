package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

/**
 * 주식 데이터를 저장하는 DTO 역할
 * symbol : 주식 심볼
 * name : 주식 이름
 * currency : 통화 종류
 * stockExchange : 거래소 이름
 * exchangeShortName : 거래소 약어 (ex. NASDAQ)
 */

@Getter
@Setter
public class Stock {
    private String symbol;
    private String name;
    private String currency;
    private String stockExchange;
    private String exchangeShortName;
}
