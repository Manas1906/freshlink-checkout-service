package com.freshlink.checkout.controller;

import com.freshlink.checkout.dto.CheckoutRequest;
import com.freshlink.checkout.model.CheckoutOrder;
import com.freshlink.checkout.service.CheckoutService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.freshlink.checkout.model.CartItem;
import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService service;

    public CheckoutController(CheckoutService service) {
        this.service = service;
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
}
