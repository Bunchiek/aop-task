package org.example.messageservice.controller;

import org.example.messageservice.model.Message;
import org.example.messageservice.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessage() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.addMessage(message));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMessages() {
        messageService.removeMessages();
        return ResponseEntity.noContent().build();
    }
}
