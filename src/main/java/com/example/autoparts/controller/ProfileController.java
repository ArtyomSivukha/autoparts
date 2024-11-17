package com.example.autoparts.controller;

import com.example.autoparts.model.Profile;
import com.example.autoparts.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public Profile getProfileByUserId(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }

    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        return profileService.saveProfile(profile);
    }

    @PutMapping("/{userId}")
    public Profile updateProfile(@PathVariable Long userId, @RequestBody Profile profileDetails) {
        return profileService.updateProfile(userId, profileDetails);
    }

    @DeleteMapping("/{userId}")
    public void deleteProfile(@PathVariable Long userId) {
        profileService.deleteProfile(userId);
    }
}