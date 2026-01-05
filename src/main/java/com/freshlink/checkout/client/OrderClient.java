package com.freshlink.checkout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface OrderClient {

    @PostMapping("/api/orders/from-checkout/{checkoutId}")
    void createOrder(@PathVariable Long checkoutId);
}
