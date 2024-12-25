package io.learnspring.tinder_ai_backend.profiles;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.learnspring.tinder_ai_backend.profiles.Gender;
import io.learnspring.tinder_ai_backend.profiles.Profile;
import io.learnspring.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProfileCreationService {

    private static final String PROFILES_FILE_PATH = "profiles.json";

    @Value("${startup-actions.initializeProfiles}")
    private Boolean initializeProfiles;

    @Value("#{${tinderai.character.user}}")
    private Map<String, String> userProfileProperties;

    private ProfileRepository profileRepository;

    public ProfileCreationService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void saveProfilesToDB() {
        Gson gson = new Gson();
        try {
            // Read existing profiles from JSON file
            List<Profile> existingProfiles = gson.fromJson(
                    new FileReader(PROFILES_FILE_PATH),
                    new TypeToken<ArrayList<Profile>>() {}.getType()
            );

            // Clear the database and save all profiles from the JSON file
            profileRepository.deleteAll();
            profileRepository.saveAll(existingProfiles);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create a new profile from the user profile properties and save it
        Profile profile = new Profile(
                userProfileProperties.get("id"),
                userProfileProperties.get("firstName"),
                userProfileProperties.get("lastName"),
                Integer.parseInt(userProfileProperties.get("age")),
                userProfileProperties.get("ethnicity"),
                Gender.valueOf(userProfileProperties.get("gender")),
                userProfileProperties.get("bio"),
                userProfileProperties.get("imageUrl"),
                userProfileProperties.get("myersBriggsPersonalityType")
        );

        // Save the new profile to the database
        profileRepository.save(profile);
    }
}
