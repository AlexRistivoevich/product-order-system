package com.example.grocery.service;

import com.example.grocery.dto.CreateReplacementRequest;
import com.example.grocery.dto.ReplacementDecisionRequest;
import com.example.grocery.entity.CustomerOrder;
import com.example.grocery.entity.Product;
import com.example.grocery.entity.ReplacementProposal;
import com.example.grocery.entity.User;
import com.example.grocery.enums.OrderStatus;
import com.example.grocery.enums.ReplacementStatus;
import com.example.grocery.exception.BadRequestException;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.ReplacementProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplacementService {
    private final ReplacementProposalRepository replacementRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    public ReplacementProposal create(Long orderId, CreateReplacementRequest request) {
        CustomerOrder order = orderService.getById(orderId);
        Product originalProduct = productService.getById(request.originalProductId());
        Product proposedProduct = productService.getById(request.proposedProductId());
        User createdBy = userService.getById(request.createdByUserId());

        ReplacementProposal proposal = new ReplacementProposal();
        proposal.setOrder(order);
        proposal.setOriginalProduct(originalProduct);
        proposal.setProposedProduct(proposedProduct);
        proposal.setCreatedBy(createdBy);
        proposal.setReason(request.reason());
        proposal.setStatus(ReplacementStatus.PENDING);
        proposal.setCreatedAt(LocalDateTime.now());

        if (order.getStatus() != OrderStatus.AWAITING_CUSTOMER_RESPONSE) {
            orderService.changeStatus(orderId,
                    new com.example.grocery.dto.ChangeOrderStatusRequest(createdBy.getId(), OrderStatus.AWAITING_CUSTOMER_RESPONSE,
                            "Replacement proposed"));
        }

        return replacementRepository.save(proposal);
    }

    public ReplacementProposal decide(Long replacementId, ReplacementDecisionRequest request) {
        ReplacementProposal proposal = replacementRepository.findById(replacementId)
                .orElseThrow(() -> new ResourceNotFoundException("Replacement proposal not found: " + replacementId));

        if (request.status() == ReplacementStatus.PENDING) {
            throw new BadRequestException("Decision status cannot be PENDING");
        }

        proposal.setStatus(request.status());
        proposal.setClientComment(request.clientComment());
        return replacementRepository.save(proposal);
    }

    public List<ReplacementProposal> getByOrderId(Long orderId) {
        orderService.getById(orderId);
        return replacementRepository.findByOrderId(orderId);
    }
}
