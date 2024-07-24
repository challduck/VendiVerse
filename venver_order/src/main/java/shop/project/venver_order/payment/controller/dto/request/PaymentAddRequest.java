package shop.project.venver_order.payment.controller.dto.request;


import shop.project.venver_order.util.PaymentMethod;

public record PaymentAddRequest(
        Long orderId,
        long amount,
        PaymentMethod paymentMethod
) {}