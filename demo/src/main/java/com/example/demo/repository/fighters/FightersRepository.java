package com.example.demo.repository.fighters;


import com.example.demo.entity.fighter.Fighters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightersRepository extends JpaRepository<Fighters,Long> {
}
