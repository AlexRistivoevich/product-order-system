package com.example.grocery.repository;

import com.example.grocery.entity.ReplacementProposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplacementProposalRepository extends JpaRepository<ReplacementProposal, Long> {
    List<ReplacementProposal> findByOrderId(Long orderId);
}
