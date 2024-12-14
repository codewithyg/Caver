package com.example.Caver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.Caver.model.News;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * DeepSearch News API를 활용하여 뉴스 제공 처리
 */

@Service
public class NewsService {

    @Value("${news.api.key}")
    private String apiKey;

    private static final String DOMESTIC_NEWS_URL = "https://api-v2.deepsearch.com/v1/articles";
    private static final String GLOBAL_NEWS_URL = "https://api-v2.deepsearch.com/v1/global-articles";

    private final RestTemplate restTemplate = new RestTemplate();

    // 한 번에 가져올 뉴스 수 (예: 100개)
    private static final int PAGE_SIZE = 9;

    // 국내 뉴스 처리
    public Map<String, Object> searchDomesticNews(String query, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(DOMESTIC_NEWS_URL)
                .queryParam("keyword", query)
                .queryParam("date_from", getDateParameters().get("date_from"))
                .queryParam("date_to", getDateParameters().get("date_to"))
                .queryParam("page_size", PAGE_SIZE)
                .queryParam("page", page)
                .queryParam("api_key", apiKey)
                .toUriString();

        // API 요청 및 응답 처리
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        // total_pages 값을 포함
        int totalPages = jsonResponse.getInt("total_pages");

        JSONArray articles = jsonResponse.getJSONArray("data");

        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            String title = article.getString("title");
            String urlToImage = article.optString("image_url", null);
            String articleUrl = article.getString("content_url");
            String publisher = article.getString("publisher");

            News newsItem = new News(
                    title,
                    urlToImage,
                    articleUrl,
                    publisher
            );

            newsList.add(newsItem);
        }

        // 결과를 Map으로 반환하여 total_pages와 함께 제공
        Map<String, Object> result = new HashMap<>();
        result.put("newsList", newsList);
        result.put("totalPages", totalPages);

        return result;
    }

    // 카테고리 별 국내 뉴스 처리
    public Map<String, Object> getDomesticNewsBySection(String section, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(DOMESTIC_NEWS_URL + "/" + section)
                .queryParam("date_from", getDateParameters().get("date_from"))
                .queryParam("date_to", getDateParameters().get("date_to"))
                .queryParam("page", page)
                .queryParam("page_size", PAGE_SIZE)
                .queryParam("api_key", apiKey)
                .toUriString();

        // API 요청 및 응답 처리
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        // total_pages 값을 포함
        int totalPages = jsonResponse.getInt("total_pages");

        JSONArray articles = jsonResponse.getJSONArray("data");

        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            String title = article.getString("title");
            String urlToImage = article.optString("image_url", null);
            String articleUrl = article.getString("content_url");
            String publisher = article.getString("publisher");

            News newsItem = new News(
                    title,
                    urlToImage,
                    articleUrl,
                    publisher
            );

            newsList.add(newsItem);
        }

        // 결과를 Map으로 반환하여 total_pages와 함께 제공
        Map<String, Object> result = new HashMap<>();
        result.put("newsList", newsList);
        result.put("totalPages", totalPages);

        return result;
    }

    // 해외 뉴스 처리
    public Map<String, Object> searchGlobalNews(String query, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(GLOBAL_NEWS_URL)
                .queryParam("keyword", query)
                .queryParam("date_from", getDateParameters().get("date_from"))
                .queryParam("date_to", getDateParameters().get("date_to"))
                .queryParam("page", page)
                .queryParam("page_size", PAGE_SIZE)
                .queryParam("api_key", apiKey)
                .toUriString();

        // API 요청 및 응답 처리
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        // total_pages 값을 포함
        int totalPages = jsonResponse.getInt("total_pages");

        JSONArray articles = jsonResponse.getJSONArray("data");

        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            String title = article.getString("title_ko");
            String urlToImage = article.optString("image_url", null);
            String articleUrl = article.getString("content_url");
            String publisher = article.getString("publisher");

            News newsItem = new News(
                    title,
                    urlToImage,
                    articleUrl,
                    publisher
            );

            newsList.add(newsItem);
        }

        // 결과를 Map으로 반환하여 total_pages와 함께 제공
        Map<String, Object> result = new HashMap<>();
        result.put("newsList", newsList);
        result.put("totalPages", totalPages);

        return result;
    }

    private Map<String, String> getDateParameters() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate oneWeekAgo = today.minusWeeks(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Map.of(
                "date_from", oneWeekAgo.format(formatter),
                "date_to", today.format(formatter)
        );
    }
}
