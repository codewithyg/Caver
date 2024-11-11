package com.example.Caver.model;
import lombok.Getter;
import lombok.Setter;

public class News {

    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String urlToImage;
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String sourceName;

    public News(String title, String urlToImage, String url, String sourceName) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
        this.sourceName = sourceName;
    }

}