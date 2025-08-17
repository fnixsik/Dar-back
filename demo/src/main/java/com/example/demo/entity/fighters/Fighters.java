package com.example.demo.entity.fighters;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fighters")

public class Fighters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String birthplace;
    private String weightClass;
    private String record;
    private String tko;
    private String solution;
    private String submissive;
    private String other;
    private String country;
    private String sport;
    private String rank;
    private String instagram;
    private String img;

    public Fighters() {
    }

    public Fighters(String name, String nickname, String birthplace, String weightClass, String record, String tko,
                    String solution, String submissive, String other, String country, String sport, String rank, String instagram,
                    String img, List<Achievement> achievements) {
        this.name = name;
        this.nickname = nickname;
        this.birthplace = birthplace;
        this.weightClass = weightClass;
        this.record = record;
        this.tko = tko;
        this.solution = solution;
        this.submissive = submissive;
        this.other = other;
        this.country = country;
        this.sport = sport;
        this.rank = rank;
        this.instagram = instagram;
        this.img = img;
        this.achievements = achievements;
    }

    @OneToMany(mappedBy = "fighter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Achievement> achievements;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getWeightClass() {
        return weightClass;
    }

    public void setWeightClass(String weightClass) {
        this.weightClass = weightClass;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getTko() {
        return tko;
    }

    public void setTko(String tko) {
        this.tko = tko;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSubmissive() {
        return submissive;
    }

    public void setSubmissive(String submissive) {
        this.submissive = submissive;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}
