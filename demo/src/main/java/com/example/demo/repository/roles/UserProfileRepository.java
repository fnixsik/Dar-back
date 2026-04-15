package com.example.demo.repository.roles;

import com.example.demo.entity.users.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findByUserId(Long userId);
}
