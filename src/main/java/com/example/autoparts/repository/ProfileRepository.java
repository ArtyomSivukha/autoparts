package com.example.autoparts.repository;

import com.example.autoparts.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserId(Long userId);  // Метод для поиска профиля по ID пользователя
}