package com.freshlink.checkout.controller;

import com.freshlink.checkout.dto.CheckoutRequest;
import com.freshlink.checkout.model.CheckoutOrder;
import com.freshlink.checkout.service.CheckoutService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

}
