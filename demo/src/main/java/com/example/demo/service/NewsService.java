package com.example.demo.service;

import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews(){
        return newsRepository.findAll();
    }
}
