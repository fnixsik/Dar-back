package com.example.demo.controller.fighters;

import com.example.demo.entity.fighters.Fighters;
import com.example.demo.service.fighters.FightersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FightersController {

    @Autowired
    private FightersService fightersService;

    @GetMapping("/v1/fighters")
    public List<Fighters> getAllFighters(){
        return fightersService.getAllFighters();
    }

    @PostMapping("/v1/fighters")
    public Fighters createFighters(@RequestBody Fighters fighters){
        return fightersService.createFighters(fighters);
    }
}
