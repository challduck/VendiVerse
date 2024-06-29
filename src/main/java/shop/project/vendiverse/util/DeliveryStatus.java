package shop.project.vendiverse.util;

public enum DeliveryStatus {
    PENDING, // 배송 대기 중
    SHIPPED, // 배송 중
    IN_TRANSIT, // 운송 중
    OUT_FOR_DELIVERY, // 배송 출발
    DELIVERED, // 배송 완료
    RETURNED, // 반품됨
    CANCELLED, // 배송 취소됨
    FAILED // 배송 실패
}
