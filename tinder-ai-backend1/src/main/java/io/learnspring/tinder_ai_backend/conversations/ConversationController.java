package io.learnspring.tinder_ai_backend.conversations;

import io.learnspring.tinder_ai_backend.profiles.Profile;
import io.learnspring.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

//@RestController
//public class ConversationController {
//
//    private final ConversationRepository conversationRepository;
//    private final ProfileRepository profileRepository;
//
//    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository) {
//        this.conversationRepository = conversationRepository;
//        this.profileRepository = profileRepository;
//    }
//
//
//    @CrossOrigin(origins = "*")
//    @PostMapping("/conversations")
//    public Conversations createNewConversation(@RequestBody createConversationRequest request ){
//        profileRepository.findById(request.profileId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        Conversations conversation = new Conversations(
//                UUID.randomUUID().toString(),
//                request.profileId(),
//                new ArrayList<>()
//        );
//        conversationRepository.save(conversation);
//        return conversation;
//    }
//
//    public record createConversationRequest(
//            String profileId
//    ){}
//
//
//    @CrossOrigin(origins = "*")
//    @GetMapping("/conversations/{conversationId}")
//    public Conversations getConversation(
//            @PathVariable String conversationId
//            ){
//
//                return  conversationRepository.findById(conversationId)
//                    .orElseThrow(() -> new ResponseStatusException(
//                            HttpStatus.NOT_FOUND,
//                            "Unable to find the conversationId."));
//
//    }
//
//
//    @CrossOrigin(origins = "*")
//    @PostMapping("/conversations/{conversationId}")
//    public Conversations addMessageToConversation(
//            @PathVariable String conversationId,
//            @RequestBody ChatMessage chatMessage
//    ) {
//        Conversations conversation = conversationRepository.findById(conversationId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Unable to find conversation with the ID " + conversationId
//                ));
//        String matchProfileId = conversation.profileId();
//
//        Profile profile = profileRepository.findById(matchProfileId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Unable to find a profile with ID " + matchProfileId
//                ));
//        Profile user = profileRepository.findById(chatMessage.authorId())
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Unable to find a profile with ID " + chatMessage.authorId()
//                ));
//
//    @CrossOrigin(origins = "*")
//    @PostMapping("/conversations/{conversationId}")
//    public Conversations addMessageToConversation(
//            @PathVariable String conversationId,
//            @RequestBody ChatMessage chatMessage
//    ){
//        Conversations conversations = conversationRepository.findById(conversationId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Unable to find the conversationId."));
//
//        profileRepository.findById(chatMessage.authorId())
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Unable to find the profile associated with the conversation"));
//
//        ChatMessage messageWithTime = new ChatMessage(
//                chatMessage.messageText(),
//                chatMessage.authorId(),
//                LocalDateTime.now()
//        );
//        conversations.messages().add(messageWithTime);
//        conversationRepository.save(conversations);
//        return conversations;
//    }
//}
@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/conversations/{conversationId}")
    public Conversations getConversation(
            @PathVariable String conversationId
    ) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/conversations/{conversationId}")
    public Conversations addMessageToConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
    ) {
        Conversations conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
        String matchProfileId = conversation.profileId();

        Profile profile = profileRepository.findById(matchProfileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + matchProfileId
                ));
        Profile user = profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + chatMessage.authorId()
                ));

        // TODO: Need to validate that the author of a message happens to be only the profile associated with the message or the user ONLY

        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        conversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        return conversation;

    }
}