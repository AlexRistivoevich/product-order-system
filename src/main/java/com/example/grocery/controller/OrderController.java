package com.example.grocery.controller;

import com.example.grocery.dto.ChangeOrderStatusRequest;
import com.example.grocery.dto.CreateOrderRequest;
import com.example.grocery.entity.CustomerOrder;
import com.example.grocery.entity.OrderStatusHistory;
import com.example.grocery.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public CustomerOrder create(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping
    public List<CustomerOrder> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerOrder getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PatchMapping("/{id}/status")
    public CustomerOrder changeStatus(@PathVariable Long id,
                                      @Valid @RequestBody ChangeOrderStatusRequest request) {
        return orderService.changeStatus(id, request);
    }

    @GetMapping("/{id}/history")
    public List<OrderStatusHistory> getHistory(@PathVariable Long id) {
        return orderService.getHistory(id);
    }
}
