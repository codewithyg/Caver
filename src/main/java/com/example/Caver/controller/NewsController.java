package com.example.Caver.controller;

import com.example.Caver.service.NewsService;
import com.example.Caver.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * 뉴스 정보를 제공하는 기능을 제어하는 컨트롤러
 */

@Controller
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // 뉴스 검색 결과 제공
    @GetMapping("/news")
    public String showNews(
            @RequestParam(defaultValue = "all") String query,
            @RequestParam(defaultValue = "relevancy") String sortBy,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        int articlesPerPage = 9;
        int totalArticles = newsService.getTotalNews(query);
        int totalPages = (int) Math.ceil((double) totalArticles / articlesPerPage);

        totalPages = Math.min(totalPages, 10);

        List<News> newsData = newsService.getNews(query, sortBy, page);

        int groupSize = 5;
        int currentGroup = (page - 1) / groupSize;
        int startPage = currentGroup * groupSize + 1;
        int endPage = Math.min(startPage + groupSize - 1, totalPages);

        model.addAttribute("newsData", newsData);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("query", query);
        model.addAttribute("sortBy", sortBy);

        return "news";
    }

    // 최신 헤드라인 뉴스 제공
    @GetMapping("/news/head")
    public String showHeadLines(@RequestParam(defaultValue = "1") int page, Model model) {
        int articlesPerPage = 9; // 한 페이지당 기사 수
        int totalArticles = newsService.getTotalHeadLines();
        int totalPages = (int) Math.ceil((double) totalArticles / articlesPerPage);

        List<News> newsData = newsService.getHeadLines(page);

        // 페이지네이션
        int groupSize = 5;
        int currentGroup = (page - 1) / groupSize;
        int startPage = currentGroup * groupSize + 1;
        int endPage = Math.min(startPage + groupSize - 1, totalPages);

        model.addAttribute("newsData", newsData);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "headnews";
    }

    // 카테고리별 최신 헤드라인 뉴스 제공
    @GetMapping("/news/head/{category}")
    public String showCategoryNews(@PathVariable String category,
                                    @RequestParam(defaultValue = "1") int page,
                                    Model model) {
        int articlesPerPage = 9;
        int totalArticles = newsService.getTotalHeadLinesByCategory(category);
        int totalPages = (int) Math.ceil((double) totalArticles / articlesPerPage);

        totalPages = Math.min(totalPages, 10);

        List<News> categoryNews = newsService.getHeadLinesByCategory(category, page);

        int groupSize = 5;
        int currentGroup = (page - 1) / groupSize;
        int startPage = currentGroup * groupSize + 1;
        int endPage = Math.min(startPage + groupSize - 1, totalPages);

        model.addAttribute("newsData", categoryNews);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "headnews";
    }
}