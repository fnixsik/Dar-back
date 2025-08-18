package com.example.demo.controller.fighters;

import com.example.demo.entity.fighters.Achievement;
import com.example.demo.service.fighters.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AchievementController {
    @Autowired
    private AchievementService achievementService;

    @GetMapping("/v1/fighters/status")
    public List<Achievement> getAllAchievements(){
        return achievementService.getAllAchievements();
    }

    @PostMapping("/v1/fighters/status")
    public Achievement createAchievementRepository(@RequestBody Achievement achievement){
        return achievementService.createAchievementRepository(achievement);
    }
}
