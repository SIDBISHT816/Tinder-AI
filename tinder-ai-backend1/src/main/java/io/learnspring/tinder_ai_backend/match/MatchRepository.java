package io.learnspring.tinder_ai_backend.match;

import io.learnspring.tinder_ai_backend.profiles.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match,String> {
}
