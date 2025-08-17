package com.example.demo.entity.fighters;

import jakarta.persistence.*;

@Entity
@Table(name = "achievements")

public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fighter_id", nullable = false)
    private Fighters fighter;

    public Achievement() {
    }

    public Achievement(String title, Fighters fighters) {
        this.title = title;
        this.fighter = fighters;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fighters getFighters() {
        return fighter;
    }

    public void setFighters(Fighters fighters) {
        this.fighter = fighters;
    }
}
