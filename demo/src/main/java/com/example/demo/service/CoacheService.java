package com.example.demo.service;

import com.example.demo.dto.coache.CoacheDTO;
import com.example.demo.dto.coache.MeritDTO;
import com.example.demo.entity.coach.Coache;
import com.example.demo.entity.coach.Merit;
import com.example.demo.entity.fighter.Fighters;
import com.example.demo.repository.coache.CoacheRepository;
import com.example.demo.repository.coache.MeritRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CoacheService {
    private final CoacheRepository coacheRepository;
    private final MeritRepository meritRepository;

    public CoacheService(CoacheRepository coacheRepository, MeritRepository meritRepository) {
        this.coacheRepository = coacheRepository;
        this.meritRepository = meritRepository;
    }

    // Создать Тренера
    public CoacheDTO createCoache(Coache coache) {
        Coache saved = coacheRepository.save(coache);
        return mapToDTO(saved);
    }

    public CoacheDTO deleteCoache( Long id) {
        Coache coache = coacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coache not found"));
        coacheRepository.delete(coache);
        return mapToDTO(coache);
    }

    public CoacheDTO updateCoache(Long id, Coache updatedData) {
        Coache coache = coacheRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Coache not found with id: " + id));

        // ИСКЛЮЧАЕМ ПРАВИЛЬНЫЕ ПОЛЯ
        BeanUtils.copyProperties(updatedData, coache, "id", "merit");

        Coache saved = coacheRepository.save(coache);
        return mapToDTO(saved);
    }

    // добавить достижение к Тренер
    public MeritDTO addMerit(Long coacheId, String list){
        Coache coache = coacheRepository.findById(coacheId)
                .orElseThrow(() -> new RuntimeException("coache not found"));

        Merit merit = new Merit();
        merit.setList(list);
        merit.setCoache(coache);

        Merit saved = meritRepository.save(merit);
        return mapMeritDTO(saved);
    }

    // получить Тренер с достижениями
    public CoacheDTO getCoache(Long id){
        Coache coache = coacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("coache not found"));
        return mapToDTO(coache);
    }

    public List<CoacheDTO> getAllCoaches(){
        List<Coache> list = coacheRepository.findAll();
        return list.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================
    // 🔹 Маппинг Entity → DTO
    // ==========================

    private CoacheDTO mapToDTO(Coache coache){
        CoacheDTO dto = new CoacheDTO();
        dto.setId(coache.getId());
        dto.setName(coache.getName());
        dto.setStatus(coache.getStatus());
        dto.setImg(coache.getImg());

        List<MeritDTO> merit = coache.getMerit().stream()
                .map(this::mapMeritDTO)
                .toList();

        dto.setMerit(merit);
        return dto;
    }

    private MeritDTO  mapMeritDTO(Merit merit){
        MeritDTO dto = new MeritDTO();
        dto.setId(merit.getId());
        dto.setList(merit.getList());
        return dto;
    }
}

