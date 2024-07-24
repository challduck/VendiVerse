package shop.project.venver_order.order.service.dto.res;

import lombok.Builder;
import shop.project.venver_order.util.OrderStatus;

import java.time.LocalDateTime;

public record OrderListResponse (String productName, OrderStatus status, long amount, long quantity, LocalDateTime orderedAt){
    @Builder
    public OrderListResponse{

    }
}
