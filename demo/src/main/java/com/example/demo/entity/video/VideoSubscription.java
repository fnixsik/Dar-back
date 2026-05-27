package com.example.demo.entity.video;

import com.example.demo.entity.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video_subscriptions")
public class VideoSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(name = "video_link", nullable = false)
    private String videoLink;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt = LocalDateTime.now();

    // Метод проверки: просрочена ли подписка
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
