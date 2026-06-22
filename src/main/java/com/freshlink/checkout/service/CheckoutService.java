package com.freshlink.checkout.service;

import com.freshlink.checkout.client.CartClient;
import com.freshlink.checkout.client.CartResponse;
import com.freshlink.checkout.dto.CheckoutRequest;
import com.freshlink.checkout.model.*;
import com.freshlink.checkout.repository.CartItemRepository;
import com.freshlink.checkout.repository.CheckoutOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckoutService {

    private final CheckoutOrderRepository repo;
    private final CartItemRepository cartItemRepo;
    private final CartClient cartClient;

    public CheckoutService(CheckoutOrderRepository repo, CartItemRepository cartItemRepo, CartClient cartClient) {
        this.repo = repo;
        this.cartItemRepo = cartItemRepo;
        this.cartClient = cartClient;
    }

    @Transactional
    public CheckoutOrder checkout(String token, CheckoutRequest req) {

        CartResponse cart = cartClient.myCart(token);

        if (cart.items == null || cart.items.isEmpty())
            throw new RuntimeException("Cart empty");

        double subtotal = cart.items.stream()
                .mapToDouble(i -> i.price * i.quantity)
                .sum();

        double tax = subtotal * 0.05;
        double deliveryFee = 40;
        double payable = subtotal + tax + deliveryFee;

        CheckoutOrder order = CheckoutOrder.builder()
                .customerEmail(cart.customerEmail)
                .cartId(req.getCartId())
                .totalAmount(subtotal)
                .taxAmount(tax)
                .deliveryFee(deliveryFee)
                .payableAmount(payable)
                .status(CheckoutStatus.PENDING_PAYMENT)
                .createdAt(LocalDateTime.now())
                .build();

        CheckoutOrder savedOrder = repo.save(order);

        // Snapshot cart items locally in checkout service database
        for (var item : cart.items) {
            CartItem checkoutItem = new CartItem();
            checkoutItem.setCheckoutId(savedOrder.getId());
            checkoutItem.setProductId(item.productId);
            checkoutItem.setProductName(item.productName);
            checkoutItem.setPrice(item.price);
            checkoutItem.setQuantity(item.quantity);
            cartItemRepo.save(checkoutItem);
        }

        return savedOrder;
    }

    public CheckoutOrder getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Checkout order not found"));
    }

    public List<CartItem> getItems(Long checkoutId) {
        return cartItemRepo.findByCheckoutId(checkoutId);
    }
}
