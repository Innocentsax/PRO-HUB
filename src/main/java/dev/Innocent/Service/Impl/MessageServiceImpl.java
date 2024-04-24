package dev.Innocent.Service.Impl;

import dev.Innocent.Service.MessageService;
import dev.Innocent.Service.ProjectService;
import dev.Innocent.model.Chat;
import dev.Innocent.model.Message;
import dev.Innocent.model.User;
import dev.Innocent.repository.MessageRepository;
import dev.Innocent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private ProjectService projectService;
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              UserRepository userRepository,
                              ProjectService projectService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new Exception("User not found with Id: " + senderId));
        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMessage = messageRepository.save(message);

        chat.getMessage().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        List<Message> findByChatIdOrderByCreatedAtAsc = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtAsc;
    }
}
