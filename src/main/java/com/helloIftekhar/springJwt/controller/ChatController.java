package com.helloIftekhar.springJwt.controller;

import com.helloIftekhar.springJwt.model.ChatMessage;
import com.helloIftekhar.springJwt.model.User;
import com.helloIftekhar.springJwt.service.Chatservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private Chatservice chatService;

    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();

        String firstName = userDetails.getFirstName(); // Assuming you have implemented this method
        String lastName = userDetails.getLastName(); // Assuming you have implemented this method

        String sender = firstName + " " + lastName; // Concatenate first name and last name
        message.setSender(sender);
        chatService.sendMessage(message);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory() {
        List<ChatMessage> chatHistory = chatService.getChatHistory();
        return ResponseEntity.ok(chatHistory);
    }
}
