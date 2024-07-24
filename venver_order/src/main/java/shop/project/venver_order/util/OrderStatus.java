package shop.project.venver_order.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDERED("주문 완료", 1),
    SHIPPED("배송 중", 2),
    DELIVERED("배송 완료", 3),
    CANCELLED("주문 취소", 4),
    RETURNED("반품 처리", 5);

    private final String Description;
    private final int code;

}
