package com.example.grocery.entity;

import com.example.grocery.enums.ReplacementStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "replacement_proposals")
@Getter
@Setter
@NoArgsConstructor
public class ReplacementProposal extends BaseEntity {
    @ManyToOne(optional = false)
    private CustomerOrder order;

    @ManyToOne(optional = false)
    private Product originalProduct;

    @ManyToOne(optional = false)
    private Product proposedProduct;

    @ManyToOne(optional = false)
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReplacementStatus status;

    @Column(length = 1000)
    private String reason;

    @Column(length = 1000)
    private String clientComment;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
