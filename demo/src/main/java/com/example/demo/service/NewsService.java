package com.example.demo.service;

import com.example.demo.dto.NewsDTO;
import com.example.demo.dto.fighter.FighterDTO;
import com.example.demo.entity.News;
import com.example.demo.entity.fighter.Fighters;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // Получить все новости
    public List<News> getAllNews(){
        return newsRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    // Создать новость
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    public NewsDTO updateNews(Long id, News updatedData) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("News not found with id: " + id));

        // копируем все совпадающие поля, кроме тех, что не надо трогать
        BeanUtils.copyProperties(updatedData, news, "id", "achievements");

        News saved = newsRepository.save(news);
        return mapToDTO(saved);
    }

    public NewsDTO deleteNews(Long id) {
        News news =  newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));

        newsRepository.delete(news);

        return  mapToDTO(news);
    }

    public NewsDTO getnews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
        return mapToDTO(news);
    }

    private NewsDTO mapToDTO(News news) {
        NewsDTO dto = new NewsDTO();
        BeanUtils.copyProperties(news, dto);
        return dto;
    }
}
