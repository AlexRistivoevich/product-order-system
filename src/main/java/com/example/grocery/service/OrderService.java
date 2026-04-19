package com.example.grocery.service;

import com.example.grocery.dto.ChangeOrderStatusRequest;
import com.example.grocery.dto.CreateOrderItemRequest;
import com.example.grocery.dto.CreateOrderRequest;
import com.example.grocery.entity.*;
import com.example.grocery.enums.OrderStatus;
import com.example.grocery.exception.BadRequestException;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.CustomerOrderRepository;
import com.example.grocery.repository.OrderStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerOrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final UserService userService;
    private final ProductService productService;

    @Transactional
    public CustomerOrder create(CreateOrderRequest request) {
        User client = userService.getById(request.clientId());
        User picker = request.pickerId() != null ? userService.getById(request.pickerId()) : null;

        CustomerOrder order = new CustomerOrder();
        order.setOrderNumber(generateOrderNumber());
        order.setClient(client);
        order.setPicker(picker);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setDeliveryAddress(request.deliveryAddress());
        order.setComment(request.comment());

        for (CreateOrderItemRequest itemRequest : request.items()) {
            Product product = productService.getById(itemRequest.productId());
            if (product.getAvailableQuantity() < itemRequest.quantity()) {
                throw new BadRequestException("Not enough quantity for product: " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setPriceAtOrderTime(product.getPrice());
            order.getItems().add(item);
        }

        CustomerOrder savedOrder = orderRepository.save(order);
        saveStatusHistory(savedOrder, OrderStatus.CREATED, OrderStatus.CREATED, client, "Order created");
        return savedOrder;
    }

    public List<CustomerOrder> findAll() {
        return orderRepository.findAll();
    }

    public CustomerOrder getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    @Transactional
    public CustomerOrder changeStatus(Long orderId, ChangeOrderStatusRequest request) {
        CustomerOrder order = getById(orderId);
        User changedBy = userService.getById(request.changedByUserId());
        OrderStatus oldStatus = order.getStatus();
        order.setStatus(request.newStatus());
        CustomerOrder updatedOrder = orderRepository.save(order);
        saveStatusHistory(updatedOrder, oldStatus, request.newStatus(), changedBy, request.comment());
        return updatedOrder;
    }

    public List<OrderStatusHistory> getHistory(Long orderId) {
        getById(orderId);
        return historyRepository.findByOrderIdOrderByChangedAtAsc(orderId);
    }

    private void saveStatusHistory(CustomerOrder order,
                                   OrderStatus oldStatus,
                                   OrderStatus newStatus,
                                   User changedBy,
                                   String comment) {
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(changedBy);
        history.setComment(comment);
        history.setChangedAt(LocalDateTime.now());
        historyRepository.save(history);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
