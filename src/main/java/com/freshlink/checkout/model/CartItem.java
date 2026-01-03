package com.freshlink.checkout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_item", schema = "freshlink_cart")
@Getter @Setter
public class CartItem {

    @Id
    private Long id;

    @Column(name = "cart_id")
    private Long cartId;

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
