package io.learnspring.tinder_ai_backend.conversations;

import java.util.List;

public record Conversations(
        String id,
        String profileId,
        List<ChatMessage> messages
){
}
