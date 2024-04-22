package dev.Innocent.Service.Impl;

import dev.Innocent.Service.ChatService;
import dev.Innocent.model.Chat;
import dev.Innocent.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
