package com.example.grocery.config;

import com.example.grocery.entity.*;
import com.example.grocery.enums.OrderStatus;
import com.example.grocery.enums.ReplacementStatus;
import com.example.grocery.enums.Role;
import com.example.grocery.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            ProductRepository productRepository,
            CustomerOrderRepository orderRepository,
            ChatMessageRepository chatMessageRepository,
            ReplacementProposalRepository replacementRepository
    ) {
        return args -> {
            System.out.println("DATA INITIALIZER STARTED");
            if (userRepository.count() > 0) {
                return;
            }

            User client = new User();
            client.setFullName("Иван Петров");
            client.setEmail("ivan@mail.ru");
            client.setPhone("+78888888888");
            client.setRole(Role.CLIENT);
            userRepository.save(client);

            User picker = new User();
            picker.setFullName("Ольга Сидорова");
            picker.setEmail("olga@mail.ru");
            picker.setPhone("+79999999999");
            picker.setRole(Role.PICKER);
            userRepository.save(picker);

            Product bananas = new Product();
            bananas.setName("Бананы");
            bananas.setDescription("Свежие бананы");
            bananas.setPrice(BigDecimal.valueOf(3.40));
            bananas.setAvailableQuantity(50);
            bananas.setQualityStatus("GOOD");
            productRepository.save(bananas);

            Product apples = new Product();
            apples.setName("Яблоки");
            apples.setDescription("Красные яблоки");
            apples.setPrice(BigDecimal.valueOf(4.10));
            apples.setAvailableQuantity(30);
            apples.setQualityStatus("GOOD");
            productRepository.save(apples);

            CustomerOrder order = new CustomerOrder();
            order.setOrderNumber("ORD-TEST-001");
            order.setClient(client);
            order.setPicker(picker);
            order.setStatus(OrderStatus.ASSEMBLING);
            order.setCreatedAt(LocalDateTime.now());
            order.setDeliveryAddress("Минск, улица Независимости, 10");
            order.setComment("Позвоните, если бананы покажутся поврежденными");

            OrderItem item1 = new OrderItem();
            item1.setOrder(order);
            item1.setProduct(bananas);
            item1.setQuantity(2);
            item1.setPriceAtOrderTime(bananas.getPrice());
            order.getItems().add(item1);

            OrderItem item2 = new OrderItem();
            item2.setOrder(order);
            item2.setProduct(apples);
            item2.setQuantity(3);
            item2.setPriceAtOrderTime(apples.getPrice());
            order.getItems().add(item2);

            orderRepository.save(order);

            ChatMessage message = new ChatMessage();
            message.setOrder(order);
            message.setSender(picker);
            message.setMessage("Бананы слишком спелые. Могу ли я заменить их бананами высшего сорта?");
            message.setSentAt(LocalDateTime.now());
            chatMessageRepository.save(message);

            ReplacementProposal replacement = new ReplacementProposal();
            replacement.setOrder(order);
            replacement.setOriginalProduct(bananas);
            replacement.setProposedProduct(apples);
            replacement.setCreatedBy(picker);
            replacement.setStatus(ReplacementStatus.APPROVED);
            replacement.setReason("Оригинальные бананы не соответствуют требованиям к качеству");
            replacement.setClientComment("Одобрено");
            replacement.setCreatedAt(LocalDateTime.now());
            replacementRepository.save(replacement);
        };
    }
}