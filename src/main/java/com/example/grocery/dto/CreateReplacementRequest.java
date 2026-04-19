package com.example.grocery.dto;

import jakarta.validation.constraints.NotNull;

public record CreateReplacementRequest(
        @NotNull Long originalProductId,
        @NotNull Long proposedProductId,
        @NotNull Long createdByUserId,
        String reason
) {}
