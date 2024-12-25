package io.learnspring.tinder_ai_backend.match;

import io.learnspring.tinder_ai_backend.profiles.Profile;

public record Match(
        String id,
        Profile profile,
        String conversationId
) {
}
