package io.learnspring.tinder_ai_backend.match;

import io.learnspring.tinder_ai_backend.conversations.ConversationRepository;
import io.learnspring.tinder_ai_backend.conversations.Conversations;
import io.learnspring.tinder_ai_backend.profiles.Profile;
import io.learnspring.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MatchController {

    private final MatchRepository matchRepository;
    private final ProfileRepository profileRepository;
    private final ConversationRepository conversationRepository;

    public MatchController(MatchRepository matchRepository, ConversationRepository conversationRepository, ProfileRepository profileRepository) {
        this.matchRepository = matchRepository;
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    public record matchRequest(String profileId){}


    @CrossOrigin(origins = "*")
    @PostMapping("/matches")
    public Match createMatch(
            @RequestBody matchRequest request
    ) {
        Profile profile = profileRepository.findById(request.profileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This profile doesn't exist in the DB."));

        Conversations conversation = new Conversations(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        Match match = new Match(
                UUID.randomUUID().toString(),
                profile,
                conversation.id());

        matchRepository.save(match);
        return match;

    }

        @CrossOrigin(origins = "*")
        @GetMapping("/matches")
        public List<Match> getAllMatches(){
            return matchRepository.findAll();
    }

}
