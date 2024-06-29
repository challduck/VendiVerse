package shop.project.vendiverse.payment.dto.request;

import shop.project.vendiverse.util.PaymentMethod;

import java.math.BigDecimal;

public record PaymentAddRequest(
        String email,
        Long orderId,
        long amount,
        PaymentMethod paymentMethod
) {}