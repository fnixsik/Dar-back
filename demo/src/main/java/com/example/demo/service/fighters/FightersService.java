package com.example.demo.service.fighters;

import com.example.demo.dto.AchievementDTO;
import com.example.demo.dto.FighterDTO;
import com.example.demo.entity.fighters.Achievement;
import com.example.demo.entity.fighters.Fighters;
import com.example.demo.repository.fighters.AchievementRepository;
import com.example.demo.repository.fighters.FightersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FightersService {
    private final FightersRepository fighterRepository;
    private final AchievementRepository achievementRepository;

    public FightersService(FightersRepository fighterRepository, AchievementRepository achievementRepository) {
        this.fighterRepository = fighterRepository;
        this.achievementRepository = achievementRepository;
    }

    // создать бойца
    public FighterDTO createFighter(Fighters fighter) {
        Fighters saved = fighterRepository.save(fighter);
        return mapToDTO(saved);
    }

    // добавить достижение к бойцу
    public AchievementDTO addAchievement(Long fighterId, String title) {
        Fighters fighter = fighterRepository.findById(fighterId)
                .orElseThrow(() -> new RuntimeException("Fighter not found"));

        Achievement achievement = new Achievement();
        achievement.setTitle(title);
        achievement.setFighter(fighter);

        Achievement saved = achievementRepository.save(achievement);
        return mapAchievementToDTO(saved);
    }

    // получить бойца с достижениями
    public FighterDTO getFighter(Long id) {
        Fighters fighter = fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fighter not found"));
        return mapToDTO(fighter);
    }

    // ==========================
    // 🔹 Маппинг Entity → DTO
    // ==========================

    private FighterDTO mapToDTO(Fighters fighter) {
        FighterDTO dto = new FighterDTO();
        dto.setId(fighter.getId());
        dto.setName(fighter.getName());
        dto.setNickname(fighter.getNickname());
        dto.setCountry(fighter.getCountry());

        List<AchievementDTO> achievements = fighter.getAchievements().stream()
                .map(this::mapAchievementToDTO)
                .toList();

        dto.setAchievements(achievements);
        return dto;
    }

    private AchievementDTO mapAchievementToDTO(Achievement achievement) {
        AchievementDTO dto = new AchievementDTO();
        dto.setId(achievement.getId());
        dto.setTitle(achievement.getTitle());
        return dto;
    }
}

