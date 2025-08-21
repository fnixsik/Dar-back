package com.example.demo.controller.fighters;

import com.example.demo.dto.AchievementDTO;
import com.example.demo.dto.FighterDTO;
import com.example.demo.entity.fighters.Fighters;
import com.example.demo.service.fighters.FightersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fighters")
public class FightersController {
    private final FightersService fighterService;

    public FightersController(FightersService fighterService) {
        this.fighterService = fighterService;
    }

    // POST /fighters -> создать бойца
    @PostMapping
    public FighterDTO createFighter(@RequestBody Fighters fighter) {
        return fighterService.createFighter(fighter);
    }

    // POST /fighters/{id}/achievements -> добавить достижение
    @PostMapping("/{id}/achievements")
    public AchievementDTO addAchievement(@PathVariable Long id, @RequestBody AchievementDTO request) {
        return fighterService.addAchievement(id, request.getTitle());
    }

    // GET /fighters/{id} -> получить бойца с ачивками
    @GetMapping("/{id}")
    public FighterDTO getFighter(@PathVariable Long id) {
        return fighterService.getFighter(id);
    }
}
