package com.example.demo.service.fighters;

import com.example.demo.entity.fighters.Fighters;
import com.example.demo.repository.fighters.FightersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FightersService {
    @Autowired
    private FightersRepository fightersRepository;

    // Получить все Бойцов
    public  List<Fighters> getAllFighters(){
        return fightersRepository.findAll();
    }

    // Создать Бойца
    public Fighters createFighters(Fighters fighters){
        return fightersRepository.save(fighters);
    }
}
