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
        List<News> newsData = newsService.getLatestNews(page);
        
        model.addAttribute("newsData", newsData);
        model.addAttribute("currentPage", page); // 현재 페이지 정보 전달
        
        return "news"; // news.html 템플릿으로 이동
    }

    // 카테고리별 뉴스 페이지 (페이징 처리 포함)
    @GetMapping("/news/{category}")
    public String showCategoryNews(@PathVariable String category,
                                @RequestParam(defaultValue = "1") int page,
                                Model model) {
        List<News> categoryNews = newsService.getNewsByCategory(category, page);
        
        model.addAttribute("newsData", categoryNews);
        model.addAttribute("selectedCategory", category); // 선택된 카테고리 정보를 전달
        model.addAttribute("currentPage", page); // 현재 페이지 정보 전달
        
        return "news"; // 동일한 news.html 템플릿 사용
    }
}