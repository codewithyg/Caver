package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

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