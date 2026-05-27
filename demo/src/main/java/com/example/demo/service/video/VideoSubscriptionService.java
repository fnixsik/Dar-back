package com.example.demo.service.video;

import com.example.demo.dto.video.VideoSubscriptionDTO;
import com.example.demo.entity.users.User;
import com.example.demo.entity.video.VideoSubscription;
import com.example.demo.repository.roles.UserRepository;
import com.example.demo.repository.video.VideoSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoSubscriptionService {

    private final VideoSubscriptionRepository videoSubscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public VideoSubscriptionDTO create(String username, Long videoId, String videoLink) { // <- ДОБАВИТЬ СЮДА
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(30);

        // Вот теперь на строке 35 переменная videoLink станет видимой!
        VideoSubscription videoSubscription = VideoSubscription.builder()
                .user(user)
                .videoId(videoId)
                .videoLink(videoLink) // Строка 35 больше не будет гореть красным
                .activatedAt(now)
                .expiresAt(expiresAt)
                .build();

        VideoSubscription savedSubscription = videoSubscriptionRepository.save(videoSubscription);

        return VideoSubscriptionDTO.builder()
                .id(savedSubscription.getId())
                .userId(savedSubscription.getUser().getId())
                .videoId(savedSubscription.getVideoId())
                .videoLink(savedSubscription.getVideoLink())
                .activatedAt(savedSubscription.getActivatedAt())
                .expiresAt(savedSubscription.getExpiresAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<VideoSubscriptionDTO> getActiveSubscriptions(String username) {
        LocalDateTime now = LocalDateTime.now();

        // 1. Получаем список активных сущностей из базы
        List<VideoSubscription> subscriptions = videoSubscriptionRepository
                .findAllActiveSubscriptions(username, now);

        // 2. Превращаем этот список в список DTO
        return subscriptions.stream()
                .map(sub -> VideoSubscriptionDTO.builder()
                        .id(sub.getId())
                        .userId(sub.getUser().getId())
                        .videoId(sub.getVideoId())
                        .videoLink(sub.getVideoLink())
                        .activatedAt(sub.getActivatedAt())
                        .expiresAt(sub.getExpiresAt())
                        .build()) // isExpired высчитается автоматически в геттере DTO
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean hasActiveAccess(String username, Long videoId) {
        return videoSubscriptionRepository.hasActiveAccess(username, videoId, LocalDateTime.now());
    }

}
