package com.freshlink.checkout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.List;

@FeignClient(name = "cart-service", url = "http://localhost:8084")
public interface CartClient {

    @GetMapping("/api/cart/me")
    CartResponse myCart(@RequestHeader("Authorization") String token);
}
