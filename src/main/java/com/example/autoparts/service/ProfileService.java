package com.example.autoparts.service;

import com.example.autoparts.model.Profile;
import com.example.autoparts.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.autoparts.controller.utils.UsersUtil;
import com.example.autoparts.model.Profile;
import com.example.autoparts.model.User;
import com.example.autoparts.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    public Profile getCurrentUserProfile() {
        // Получаем текущего пользователя
        String customerEmail = UsersUtil.getCurrentUserEmail();
        User user = userService.findByEmail(customerEmail);

        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + customerEmail);
        }

        // Проверяем, есть ли у пользователя профиль
        Profile profile = user.getProfile();
        if (profile == null) {
            // Создаём новый профиль, если его нет
            profile = new Profile();
            profile.setUser(user);
            profile = profileRepository.save(profile);
        }

        return profile;
    }

    public Profile updateCurrentUserProfile(Profile updatedProfile) {
        // Получаем текущий профиль пользователя
        Profile profile = getCurrentUserProfile();

        // Обновляем поля профиля, если они переданы
        if (updatedProfile.getFirstName() != null) {
            profile.setFirstName(updatedProfile.getFirstName());
        }
        if (updatedProfile.getLastName() != null) {
            profile.setLastName(updatedProfile.getLastName());
        }
        if (updatedProfile.getAddress() != null) {
            profile.setAddress(updatedProfile.getAddress());
        }
        if (updatedProfile.getPhoneNumber() != null) {
            profile.setPhoneNumber(updatedProfile.getPhoneNumber());
        }

        // Сохраняем обновлённый профиль
        return profileRepository.save(profile);
    }



    // Создание профиля для нового пользователя
    public void createProfileForUser(User user) {
        if (user.getProfile() == null) {
            Profile profile = new Profile();
            profile.setUser(user);
            profileRepository.save(profile);
        }
    }

    // Убедиться, что у пользователя есть профиль
    public void ensureProfileForUser(User user) {
        if (user.getProfile() == null) {
            createProfileForUser(user);
        }
    }
}