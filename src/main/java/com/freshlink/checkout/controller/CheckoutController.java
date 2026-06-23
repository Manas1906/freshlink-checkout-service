package com.freshlink.checkout.controller;

import com.freshlink.checkout.dto.CheckoutRequest;
import com.freshlink.checkout.model.CheckoutOrder;
import com.freshlink.checkout.service.CheckoutService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.context.ApplicationEventPublisher;
import com.freshlink.event.payment.PaymentCompletedEvent;
import com.freshlink.checkout.model.CartItem;
import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService service;
    private final ApplicationEventPublisher eventPublisher;

    public CheckoutController(CheckoutService service, ApplicationEventPublisher eventPublisher) {
        this.service = service;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public CheckoutOrder checkout(@RequestBody CheckoutRequest req,
                                  @RequestHeader("Authorization") String token) {
        return service.checkout(token, req);
    }

    @GetMapping("/{id}")
    public CheckoutOrder getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/{id}/items")
    public List<CartItem> getItems(@PathVariable Long id) {
        return service.getItems(id);
    }

    @PostMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        CheckoutOrder checkout = service.getById(id);
        PaymentCompletedEvent event = new PaymentCompletedEvent(
                0L,
                id,
                checkout.getCustomerEmail(),
                checkout.getPayableAmount()
        );
        eventPublisher.publishEvent(event);
    }
}
