package shop.project.venver_order.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    NAVER_PAY("Naver Pay"),
    KAKAO_PAY("Kakao Pay"),
    TOSS_PAY("Toss Pay"),
    BANK_TRANSFER("Bank Transfer"),
    OTHER("Other");

    private final String label;
}
