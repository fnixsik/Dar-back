package com.example.demo.service.fighters;

import com.example.demo.dto.fighter.AchievementDTO;
import com.example.demo.dto.fighter.FighterDTO;
import com.example.demo.entity.fighter.Achievement;
import com.example.demo.entity.fighter.Fighters;
import com.example.demo.repository.fighters.AchievementRepository;
import com.example.demo.repository.fighters.FightersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public FighterDTO updateFighter(Long id, Fighters updatedData) {
        Fighters fighter = fighterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fighter not found with id: " + id));

        // копируем все совпадающие поля, кроме тех, что не надо трогать
        BeanUtils.copyProperties(updatedData, fighter, "id", "achievements");

        Fighters saved = fighterRepository.save(fighter);
        return mapToDTO(saved);
    }

    public FighterDTO deleteFighter(Long id) {
        Fighters fighter =  fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fighter not found"));

        fighterRepository.delete(fighter);

        return  mapToDTO(fighter);
    }

    public List<FighterDTO> getAllFighters() {
        List<Fighters> list = fighterRepository.findAll();
        return list.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================
    // 🔹 Маппинг Entity → DTO
    // ==========================

    private FighterDTO mapToDTO(Fighters fighter) {
        FighterDTO dto = new FighterDTO();
        dto.setId(fighter.getId());
        dto.setName(fighter.getName());
        dto.setNickname(fighter.getNickname());
        dto.setBirthplace(fighter.getBirthplace());
        dto.setWeightClass(fighter.getWeightClass());
        dto.setRecord(fighter.getRecord());
        dto.setTko(fighter.getTko());
        dto.setSolution(fighter.getSolution());
        dto.setSubmissive(fighter.getSubmissive());
        dto.setOther(fighter.getOther());
        dto.setCountry(fighter.getCountry());
        dto.setSport(fighter.getSport());
        dto.setRank(fighter.getRank());
        dto.setInstagram(fighter.getInstagram());
        dto.setImg(fighter.getImg());

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

