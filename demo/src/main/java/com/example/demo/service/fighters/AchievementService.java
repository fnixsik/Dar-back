package com.example.demo.service.fighters;

import com.example.demo.entity.fighters.Achievement;
import com.example.demo.repository.fighters.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {
    @Autowired
    private AchievementRepository achievementRepository;

    // Получить все Достижения
    public List<Achievement> getAllAchievements(){
        return achievementRepository.findAll();
    }

    public Achievement createAchievementRepository(Achievement achievement){
        return achievementRepository.save(achievement);
    }

}
