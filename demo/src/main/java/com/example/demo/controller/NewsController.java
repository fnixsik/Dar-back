package com.example.demo.controller;

import com.example.demo.dto.NewsDTO;
import com.example.demo.dto.coache.CoacheDTO;
import com.example.demo.dto.fighter.FighterDTO;
import com.example.demo.entity.fighter.Fighters;
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

    @PostMapping
    public News createNews(@RequestBody News news){
         return newsService.createNews(news);
    }

    @DeleteMapping("/{id}")
    public NewsDTO deleteNews(@PathVariable Long id) {
        return newsService.deleteNews(id);
    }

    @DeleteMapping("/{id}/image")
    public NewsDTO deleteNewsImage(@PathVariable Long id) {
        return newsService.deleteNewsImage(id);
    }

    @PutMapping("/{id}")
    public NewsDTO updateNews(@PathVariable Long id, @RequestBody News news) {
        return newsService.updateNews(id, news);
    }

    @GetMapping("/{id}")
    public NewsDTO getnews(@PathVariable Long id) {
        return newsService.getnews(id);
    }
}
