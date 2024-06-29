package shop.project.vendiverse.util;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}
