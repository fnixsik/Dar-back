package com.example.demo.controller.fighters;

import com.example.demo.entity.fighters.Achievement;
import com.example.demo.service.fighters.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PostMapping
    public Achievement createAchievement(@RequestParam Long fighterId,
                                         @RequestParam String title) {
        return achievementService.createAchievement(fighterId, title);
    }
}

