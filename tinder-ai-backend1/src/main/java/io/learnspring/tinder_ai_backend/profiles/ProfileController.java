package io.learnspring.tinder_ai_backend.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/profiles/random")
    public Profile getRandonProfile(){
        return profileRepository.getProfile();
    }
}
