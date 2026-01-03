package com.freshlink.checkout.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "checkout_order", schema = "freshlink_checkout")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CheckoutOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerEmail;

    @Column(name = "cart_id")
    private Long cartId;

    private Double totalAmount;
    private Double taxAmount;
    private Double deliveryFee;
    private Double payableAmount;

    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    private LocalDateTime createdAt;
}
