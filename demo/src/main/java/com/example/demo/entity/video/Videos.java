package com.example.demo.entity.video;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "videos")
public class Videos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private  String description;

    @Column(name = "youtube_video_id", nullable = false, unique = true)
    private Long youtubeVideoId;

    @Column(name = "created_at")
    private LocalDateTime createdAt =  LocalDateTime.now();
}
