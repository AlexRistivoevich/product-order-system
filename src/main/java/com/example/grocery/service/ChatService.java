package com.example.grocery.service;

import com.example.grocery.dto.SendMessageRequest;
import com.example.grocery.entity.ChatMessage;
import com.example.grocery.entity.CustomerOrder;
import com.example.grocery.entity.User;
import com.example.grocery.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final OrderService orderService;
    private final UserService userService;

    public ChatMessage sendMessage(Long orderId, SendMessageRequest request) {
        CustomerOrder order = orderService.getById(orderId);
        User sender = userService.getById(request.senderId());

        ChatMessage message = new ChatMessage();
        message.setOrder(order);
        message.setSender(sender);
        message.setMessage(request.message());
        message.setSentAt(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessages(Long orderId) {
        orderService.getById(orderId);
        return chatMessageRepository.findByOrderIdOrderBySentAtAsc(orderId);
    }
}
