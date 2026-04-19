package com.example.grocery.entity;

import com.example.grocery.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
@Getter
@Setter
@NoArgsConstructor
public class OrderStatusHistory extends BaseEntity {
    @ManyToOne(optional = false)
    private CustomerOrder order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus newStatus;

    @ManyToOne(optional = false)
    private User changedBy;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime changedAt;
}
