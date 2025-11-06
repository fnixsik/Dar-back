package com.example.demo.controller;

import com.example.demo.service.NewsService;
import com.example.demo.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<News> getAllNews(){
        return newsService.getAllNews();
    }

    @PostMapping("/v1/News")
    public News createNews(@RequestBody News news){
         return newsService.createNews(news);
    }
}
