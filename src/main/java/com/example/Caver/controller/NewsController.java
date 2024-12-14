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
import java.util.Map;

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

    @GetMapping("/news")
    public String showDomesticNews(@RequestParam(defaultValue = "부산") String query,
                                   @RequestParam(defaultValue = "1") int page,
                                   Model model) {
        Map<String, Object> result = newsService.searchDomesticNews(query, page);

        List<News> newsList = (List<News>) result.get("newsList");
        int totalPages = (int) result.get("totalPages");

        model.addAttribute("newsData", newsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages);

        int startPage = ((page - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "news";
    }

    @GetMapping("/news/{section}")
    public String showDomesticNewsBySection(@PathVariable String section,
                                            @RequestParam(defaultValue = "1") int page,
                                            Model model) {
        Map<String, Object> result = newsService.getDomesticNewsBySection(section, page);

        List<News> newsList = (List<News>) result.get("newsList");
        int totalPages = (int) result.get("totalPages");

        model.addAttribute("newsData", newsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectedSection", section);
        model.addAttribute("totalPages", totalPages);

        int startPage = ((page - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "sectionnews";
    }

    @GetMapping("/global-news")
    public String showGlobalNews(@RequestParam(defaultValue = "Apple") String query,
                                 @RequestParam(defaultValue = "1") int page,
                                 Model model) {
        Map<String, Object> result = newsService.searchGlobalNews(query, page);

        List<News> newsList = (List<News>) result.get("newsList");
        int totalPages = (int) result.get("totalPages");

        model.addAttribute("newsData", newsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", totalPages);

        int startPage = ((page - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "globalnews";
    }
}
