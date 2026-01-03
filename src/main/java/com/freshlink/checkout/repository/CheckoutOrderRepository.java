package com.freshlink.checkout.repository;

import com.freshlink.checkout.model.CheckoutOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutOrderRepository extends JpaRepository<CheckoutOrder, Long> {
}
