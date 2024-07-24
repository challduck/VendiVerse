package shop.project.venver_order.util;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}
