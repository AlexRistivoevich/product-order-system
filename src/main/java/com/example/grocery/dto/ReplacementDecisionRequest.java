package com.example.grocery.dto;

import com.example.grocery.enums.ReplacementStatus;
import jakarta.validation.constraints.NotNull;

public record ReplacementDecisionRequest(
        @NotNull ReplacementStatus status,
        String clientComment
) {}
