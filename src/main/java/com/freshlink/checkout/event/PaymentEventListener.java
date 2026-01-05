package com.freshlink.checkout.event;

import com.freshlink.event.payment.PaymentCompletedEvent;
import com.freshlink.event.payment.PaymentFailedEvent;
import com.freshlink.checkout.model.CheckoutOrder;
import com.freshlink.checkout.model.CheckoutStatus;
import com.freshlink.checkout.repository.CheckoutOrderRepository;
import com.freshlink.checkout.client.OrderClient;
import com.freshlink.checkout.client.ProductClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentEventListener {

    private final CheckoutOrderRepository checkoutRepo;
    private final OrderClient orderClient;
    private final ProductClient productClient;

    public PaymentEventListener(CheckoutOrderRepository checkoutRepo,
                                OrderClient orderClient,
                                ProductClient productClient) {
        this.checkoutRepo = checkoutRepo;
        this.orderClient = orderClient;
        this.productClient = productClient;
    }

    // =================== PAYMENT SUCCESS ===================
    @Transactional
    @EventListener
    public void onPaymentSuccess(PaymentCompletedEvent event) {

        CheckoutOrder checkout = checkoutRepo.findById(event.getCheckoutId())
                .orElseThrow();

        checkout.setStatus(CheckoutStatus.PAYMENT_SUCCESS);
        checkoutRepo.save(checkout);

        // 1. Create order in Order Service
        orderClient.createOrder(event.getCheckoutId());

        // 2. Reduce stock in Product Service
        productClient.reduceStock(event.getCheckoutId());
    }

    // =================== PAYMENT FAILED ===================
    @Transactional
    @EventListener
    public void onPaymentFailed(PaymentFailedEvent event) {

        CheckoutOrder checkout = checkoutRepo.findById(event.getCheckoutId())
                .orElseThrow();

        checkout.setStatus(CheckoutStatus.PAYMENT_FAILED);
        checkoutRepo.save(checkout);
    }
}
