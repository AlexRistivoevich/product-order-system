package com.example.grocery.controller;

import com.example.grocery.dto.CreateReplacementRequest;
import com.example.grocery.dto.ReplacementDecisionRequest;
import com.example.grocery.entity.ReplacementProposal;
import com.example.grocery.service.ReplacementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplacementController {
    private final ReplacementService replacementService;

    @PostMapping("/api/orders/{orderId}/replacements")
    public ReplacementProposal create(@PathVariable Long orderId,
                                      @Valid @RequestBody CreateReplacementRequest request) {
        return replacementService.create(orderId, request);
    }

    @GetMapping("/api/orders/{orderId}/replacements")
    public List<ReplacementProposal> getByOrder(@PathVariable Long orderId) {
        return replacementService.getByOrderId(orderId);
    }

    @PatchMapping("/api/replacements/{id}/decision")
    public ReplacementProposal decide(@PathVariable Long id,
                                      @Valid @RequestBody ReplacementDecisionRequest request) {
        return replacementService.decide(id, request);
    }
}
