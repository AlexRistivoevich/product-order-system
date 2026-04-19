package com.example.grocery.dto;

import com.example.grocery.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStatusRequest(
        @NotNull Long changedByUserId,
        @NotNull OrderStatus newStatus,
        String comment
) {}
