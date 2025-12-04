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

    public CoacheDTO createCoache(Coache coache) {

        if (coache.getMerit() != null) {
            for (Merit m : coache.getMerit()) {
                m.setCoache(coache);
            }
        }

        Coache saved = coacheRepository.save(coache);
        return mapToDTO(saved);
    }

    public CoacheDTO updateCoache(Long id, Coache updated) {
        Coache existing = coacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coache not found"));

        existing.setName(updated.getName());
        existing.setStatus(updated.getStatus());
        existing.setImg(updated.getImg());

        existing.getMerit().clear();

        if (updated.getMerit() != null) {
            for (Merit m : updated.getMerit()) {
                m.setCoache(existing);
                existing.getMerit().add(m);
            }
        }

        Coache saved = coacheRepository.save(existing);
        return mapToDTO(saved);
    }

    public CoacheDTO deleteCoache(Long id) {
        Coache c = coacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coache not found"));
        coacheRepository.delete(c);
        return mapToDTO(c);
    }

    public MeritDTO addMerit(Long coacheId, String list) {
        Coache coache = coacheRepository.findById(coacheId)
                .orElseThrow(() -> new RuntimeException("Coache not found"));

        Merit m = new Merit();
        m.setList(list);
        m.setCoache(coache);
        coache.getMerit().add(m);

        coacheRepository.save(coache);

        return mapMeritDTO(m);
    }

    public CoacheDTO getCoache(Long id) {
        return mapToDTO(
                coacheRepository.findById(id).orElseThrow()
        );
    }

    public List<CoacheDTO> getAllCoaches() {
        return coacheRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CoacheDTO deleteMerit(Long coachId, Long meritId) {
        Coache coach = coacheRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        Merit merit = meritRepository.findById(meritId)
                .orElseThrow(() -> new RuntimeException("Merit not found"));

        if (!merit.getCoache().getId().equals(coachId)) {
            throw new RuntimeException("This merit does not belong to this coach");
        }

        coach.getMerit().remove(merit); // orphanRemoval = true → JPA удалит merit из БД

        Coache saved = coacheRepository.save(coach);

        return mapToDTO(saved);
    }

    private CoacheDTO mapToDTO(Coache c) {
        CoacheDTO dto = new CoacheDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setStatus(c.getStatus());
        dto.setImg(c.getImg());

        dto.setMerit(
                c.getMerit().stream()
                        .map(this::mapMeritDTO)
                        .toList()
        );

        return dto;
    }

    private MeritDTO mapMeritDTO(Merit m) {
        MeritDTO dto = new MeritDTO();
        dto.setId(m.getId());
        dto.setList(m.getList());
        return dto;
    }
}


