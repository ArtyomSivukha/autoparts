package com.example.autoparts.service;

import com.example.autoparts.model.Profile;
import com.example.autoparts.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("Profile for User ID " + userId + " not found");
        }
        return profile;
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long userId, Profile profileDetails) {
        Profile profile = getProfileByUserId(userId);
        profile.setFirstName(profileDetails.getFirstName());
        profile.setLastName(profileDetails.getLastName());
        profile.setAddress(profileDetails.getAddress());
        profile.setPhoneNumber(profileDetails.getPhoneNumber());
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long userId) {
        Profile profile = getProfileByUserId(userId);
        profileRepository.delete(profile);
    }
}