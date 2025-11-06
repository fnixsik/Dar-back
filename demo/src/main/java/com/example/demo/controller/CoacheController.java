package com.example.demo.controller;

import com.example.demo.dto.coache.CoacheDTO;
import com.example.demo.dto.coache.MeritDTO;
import com.example.demo.entity.coach.Coache;
import com.example.demo.service.CoacheService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/v1/coaches")
public class CoacheController {
    private final CoacheService coacheService;

    public CoacheController(CoacheService coacheService) {
        this.coacheService = coacheService;
    }

    // POST /coache -> создать Тренер
    @PostMapping
    public CoacheDTO createCoache(@RequestBody Coache coache){
        return coacheService.createCoache(coache);
    }

    // POST /coache/{id}/merit -> добавить достижение
    @PostMapping("/{id}/merit")
    public MeritDTO addMerit(@PathVariable Long id, @RequestBody MeritDTO request) {
        return  coacheService.addMerit(id, request.getList());
    }

    // GET /coache/{id} -> получить Тренерa с ачивками
    @GetMapping("/{id}")
    public CoacheDTO getCoache(@PathVariable Long id){
        return coacheService.getCoache(id);
    }

    @GetMapping()
    public List<CoacheDTO> getAllCoache(){
        return coacheService.getAllCoaches();
    }


}
