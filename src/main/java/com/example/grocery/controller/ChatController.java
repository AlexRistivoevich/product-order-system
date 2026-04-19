package com.example.grocery.controller;

import com.example.grocery.dto.SendMessageRequest;
import com.example.grocery.entity.ChatMessage;
import com.example.grocery.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/{orderId}/messages")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatMessage sendMessage(@PathVariable Long orderId,
                                   @Valid @RequestBody SendMessageRequest request) {
        return chatService.sendMessage(orderId, request);
    }

    @GetMapping
    public List<ChatMessage> getMessages(@PathVariable Long orderId) {
        return chatService.getMessages(orderId);
    }
}
