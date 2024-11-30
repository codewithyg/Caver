package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

/**
 * 주식의 과거 데이터를 저장하는 DTO 역할
 * 차트를 보여주기 위해 사용
 * date : 데이터의 날짜
 * open : 시가
 * high : 최고가
 * low : 최저가
 * close : 종가
 * volume : 거래량
 */

@Getter
@Setter
public class HistoricalStockData {
    private String date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
}
