package com.example.Caver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.Caver.model.News;

import java.util.*;

@Service
public class NewsService {

    @Value("${news.api.key}")
    private String apiKey;

    // 최신 뉴스를 가져오기 (페이징 포함)
    public List<News> getLatestNews(int page) {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithApiKeyAndPage = "https://newsapi.org/v2/top-headlines?country=us&pageSize=9&apiKey=" + apiKey + "&page=" + page;
        String response = restTemplate.getForObject(urlWithApiKeyAndPage, String.class);

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray articles = jsonResponse.getJSONArray("articles");

        // JSON 데이터를 News 객체로 변환
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            News newsArticle = new News(
                article.getString("title"),
                article.optString("urlToImage", "default-image-url"),
                article.getString("url"),
                article.getJSONObject("source").getString("name")
            );
            newsList.add(newsArticle);
        }

        // 뉴스 목록을 무작위로 섞음 (랜덤화)
        Collections.shuffle(newsList);

        return newsList; // 뉴스 목록 반환
    }

    // 카테고리별 뉴스 가져오기 (페이징 처리 포함)
    public List<News> getNewsByCategory(String category, int page) {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithCategoryAndPage = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&pageSize=9&page=" + page + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithCategoryAndPage, String.class);

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray articles = jsonResponse.getJSONArray("articles");

        // JSON 데이터를 News 객체로 변환
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            News newsArticle = new News(
                article.getString("title"),
                article.optString("urlToImage", "default-image-url"),
                article.getString("url"),
                article.getJSONObject("source").getString("name")
            );
            newsList.add(newsArticle);
        }

        return newsList; // 선택된 카테고리 뉴스 목록 반환
    }

    public int getTotalArticles() {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithApiKey = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithApiKey, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults"); // 총 결과 수
    }

    public int getTotalArticlesByCategory(String category) {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithCategory = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithCategory, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults");
    }
}