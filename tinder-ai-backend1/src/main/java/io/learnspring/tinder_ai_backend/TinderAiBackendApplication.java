package io.learnspring.tinder_ai_backend;

import io.learnspring.tinder_ai_backend.conversations.ChatMessage;
import io.learnspring.tinder_ai_backend.conversations.ConversationRepository;
import io.learnspring.tinder_ai_backend.conversations.Conversations;
import io.learnspring.tinder_ai_backend.match.MatchRepository;
import io.learnspring.tinder_ai_backend.profiles.Gender;
import io.learnspring.tinder_ai_backend.profiles.Profile;
import io.learnspring.tinder_ai_backend.profiles.ProfileCreationService;
import io.learnspring.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private ProfileCreationService profileCreationService;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		profileCreationService.saveProfilesToDB();
//		profileRepository.getProfile();

			clearAllData();
			profileCreationService.saveProfilesToDB();

		}

		private void clearAllData() {
			conversationRepository.deleteAll();
			matchRepository.deleteAll();
			profileRepository.deleteAll();
		}
	}

