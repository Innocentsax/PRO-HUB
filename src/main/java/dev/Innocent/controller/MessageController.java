package dev.Innocent.controller;

import dev.Innocent.DTO.request.CreateMessageRequest;
import dev.Innocent.Service.MessageService;
import dev.Innocent.Service.ProjectService;
import dev.Innocent.Service.UserService;
import dev.Innocent.model.Chat;
import dev.Innocent.model.Message;
import dev.Innocent.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/message")
public class MessageController {
    private MessageService messageService;
    private UserService userService;
    private ProjectService projectService;

    @Autowired
    public MessageController(MessageService messageService,
                             UserService userService,
                             ProjectService projectService) {
        this.messageService = messageService;
        this.userService = userService;
        this.projectService = projectService;
    }
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        if(user == null){
            throw new Exception("User not found with id " + request.getSenderId());
        }
        // This method should throw Exception if the chat is not found
        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if(chat == null){
            throw new Exception("Chat not found");
        }
        Message sendMessage = messageService.sendMessage(request.getSenderId(),
                request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sendMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
