package dev.Innocent.Service;

import dev.Innocent.model.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long projectId, String content) throws Exception;
    List<Message> getMessagesByProjectId(Long projectId) throws Exception;
}
