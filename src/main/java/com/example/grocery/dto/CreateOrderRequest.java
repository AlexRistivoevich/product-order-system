package com.example.grocery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull Long clientId,
        Long pickerId,
        @NotBlank String deliveryAddress,
        String comment,
        @NotEmpty List<@Valid CreateOrderItemRequest> items
) {}
