package com.freshlink.checkout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {

    @PostMapping("/api/products/reduce-stock/{checkoutId}")
    void reduceStock(@PathVariable Long checkoutId);
}
