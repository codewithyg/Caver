package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

/**
 * 뉴스 데이터를 저장하는 DTO 역할
 * title : 뉴스 제목
 * urlToImage : 뉴스 이미지 URL
 * url : 뉴스 기사 URL
 * sourceName : 뉴스 출처
 */

@Getter
@Setter
public class News {
    private String title;
    private String urlToImage;
    private String url;
    private String sourceName;
    
    public News(String title, String urlToImage, String url, String sourceName) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
        this.sourceName = sourceName;
    }

}