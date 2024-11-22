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

@Controller
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // 기본 최신 뉴스 페이지 (페이징 처리 포함)
    @GetMapping("/news")
    public String showLatestNews(@RequestParam(defaultValue = "1") int page, Model model) {
        int articlesPerPage = 9; // 한 페이지당 기사 수
        int totalArticles = newsService.getTotalArticles();
        int totalPages = (int) Math.ceil((double) totalArticles / articlesPerPage);

        List<News> newsData = newsService.getLatestNews(page);

        // 페이지 그룹 계산 (한 번에 5개씩 표시)
        int groupSize = 5;
        int currentGroup = (page - 1) / groupSize;
        int startPage = currentGroup * groupSize + 1;
        int endPage = Math.min(startPage + groupSize - 1, totalPages);

        model.addAttribute("newsData", newsData);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "news";
    }

    // 카테고리별 뉴스 페이지 (페이징 처리 포함)
    @GetMapping("/news/{category}")
    public String showCategoryNews(@PathVariable String category,
                                    @RequestParam(defaultValue = "1") int page,
                                    Model model) {
        int articlesPerPage = 9;
        int totalArticles = newsService.getTotalArticlesByCategory(category);
        int totalPages = (int) Math.ceil((double) totalArticles / articlesPerPage);

        List<News> categoryNews = newsService.getNewsByCategory(category, page);

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

        return "news";
    }
}