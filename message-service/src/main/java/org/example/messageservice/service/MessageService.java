package org.example.messageservice.service;

import org.example.messageservice.model.Message;
import org.example.starterlogger.annotation.Loggable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final List<Message> messages;

    public MessageService(List<Message> messages) {
        this.messages = messages;
    }

    @Loggable
    public Message addMessage(Message message) {
        messages.add(message);
        return message;
    }

    @Loggable
    public void removeMessages() {
        messages.clear();
    }

    @Loggable
    public List<Message> getAllMessages() {
        return messages;
    }
}
