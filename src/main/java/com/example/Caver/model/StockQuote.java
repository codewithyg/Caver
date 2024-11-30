package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

/**
 * 주식의 실시간 데이터를 저장하는 DTO 역할
 * symbol : 주식 심볼
 * name : 주식 이름
 * exchange : 거래소 이름
 * price : 현재 주식 가격
 */

@Getter
@Setter
public class StockQuote {
    private String symbol;
    private String name;
    private String exchange;
    private Double price;
}
