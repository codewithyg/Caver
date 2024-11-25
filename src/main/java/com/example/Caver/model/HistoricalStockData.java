package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricalStockData {
    private String date;      // 데이터의 날짜
    private Double open;      // 시장 오픈 가격
    private Double high;      // 최고가
    private Double low;       // 최저가
    private Double close;     // 마감 가격
    private Long volume;      // 거래량
}
