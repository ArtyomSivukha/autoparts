package com.example.autoparts.controller;

import com.example.autoparts.controller.utils.UsersUtil;
import com.example.autoparts.model.Profile;
import com.example.autoparts.model.User;
import com.example.autoparts.service.ProfileService;
import com.example.autoparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("me")
    public ResponseEntity<Profile> getProfile() {
        // Просто вызываем метод сервиса для получения профиля
        Profile profile = profileService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("me")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile updatedProfile) {
        // Просто вызываем метод сервиса для обновления профиля
        Profile updated = profileService.updateCurrentUserProfile(updatedProfile);
        return ResponseEntity.ok(updated);
    }
}

