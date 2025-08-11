package com.example.demo.controller;

import com.example.demo.service.NewsService;
import com.example.demo.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/v1/getAllNews")
    public List<News> getAllNews(){
        return newsService.getAllNews();
    }
}
