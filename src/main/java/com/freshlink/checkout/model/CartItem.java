package com.freshlink.checkout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "checkout_item")
@Getter @Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "checkout_id")
    private Long checkoutId;

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
