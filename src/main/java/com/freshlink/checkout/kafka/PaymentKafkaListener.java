package com.freshlink.checkout.kafka;

import com.freshlink.event.payment.PaymentCompletedEvent;
import com.freshlink.event.payment.PaymentFailedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaListener {

    private final ApplicationEventPublisher eventPublisher;

    public PaymentKafkaListener(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = "payment.success", groupId = "checkout-group")
    public void onSuccess(PaymentCompletedEvent event) {
        System.out.println("PAYMENT SUCCESS RECEIVED: " + event.getPaymentIntentId());
        eventPublisher.publishEvent(event);
    }

    @KafkaListener(topics = "payment.failed", groupId = "checkout-group")
    public void onFail(PaymentFailedEvent event) {
        System.out.println("PAYMENT FAILED RECEIVED: " + event.getPaymentIntentId());
        eventPublisher.publishEvent(event);
    }
}
