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

    // 뉴스 받기
    public List<News> getNews(String query, String sortBy, int page) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/everything?q=" + query + 
                     "&sortBy=" + sortBy + 
                     "&pageSize=9&page=" + page + 
                     "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);

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

        return newsList;
    }

    // 뉴스 결과 수
    public int getTotalNews(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/everything?q=" + query + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults");
    }


    // 최신 헤드라인 받기
    public List<News> getHeadLines(int page) {
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

        // 새로고침 시 헤드라인 섞기
        Collections.shuffle(newsList);

        // 최신 헤드라인 목록 반환
        return newsList; 
    }

    // 최신 헤드라인 결과 수
    public int getTotalHeadLines() {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithApiKey = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithApiKey, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults"); // 총 결과 수
    }

    // 카테고리별 헤드라인 받기
    public List<News> getHeadLinesByCategory(String category, int page) {
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
        
        // 카테고리별 헤드라인 목록 반환
        return newsList;
    }

    // 카테고리별 헤드라인 결과 수
    public int getTotalHeadLinesByCategory(String category) {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithCategory = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithCategory, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults");
    }
}