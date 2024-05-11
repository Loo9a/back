package com.helloIftekhar.springJwt.service;

import com.helloIftekhar.springJwt.model.ChatMessage;
import com.helloIftekhar.springJwt.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Chatservice {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage sendMessage(ChatMessage message) {
        // Set the current time before saving the message
        message.setSentTime(LocalDateTime.now());

        // Save the message to the database
        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Return the saved message
        return savedMessage;
    }

    public List<ChatMessage> getChatHistory() {
        return chatMessageRepository.findAll();
    }
}
