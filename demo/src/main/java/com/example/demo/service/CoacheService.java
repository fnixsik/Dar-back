package com.example.demo.service;

import com.example.demo.dto.coache.CoacheDTO;
import com.example.demo.dto.coache.MeritDTO;
import com.example.demo.entity.coach.Coache;
import com.example.demo.entity.coach.Merit;
import com.example.demo.repository.coache.CoacheRepository;
import com.example.demo.repository.coache.MeritRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

