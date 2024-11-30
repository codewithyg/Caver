package com.example.Caver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.Caver.model.News;
import java.util.*;

/**
 * 뉴스 API 호출 및 데이터 처리
 * newsapi.org API 사용
 */

@Service
public class NewsService {

    @Value("${news.api.key}")
    private String apiKey;

    // 검색어로 뉴스 기사 검색
    // API 호출 후 응답 JSON을 News 객체 리스트로 반환
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

    // 검색한 뉴스 기사의 총 개수를 반환
    public int getTotalNews(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/everything?q=" + query + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults");
    }


    // 최신 헤드라인 기사를 호출 (미국 기준)
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

        // 헤드라인 섞기
        Collections.shuffle(newsList);

        // 최신 헤드라인 목록 반환
        return newsList; 
    }

    // 최신 헤드라인 뉴스 총 개수 반환
    public int getTotalHeadLines() {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithApiKey = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithApiKey, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults");
    }

    // 카테고리의 헤드라인 뉴스 검색
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
        
        // 카테고리별 헤드라인 뉴스 목록 반환
        return newsList;
    }

    // 카테고리의 헤드라인 뉴스 총 개수 반환
    public int getTotalHeadLinesByCategory(String category) {
        RestTemplate restTemplate = new RestTemplate();
        String urlWithCategory = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(urlWithCategory, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getInt("totalResults");
    }
}