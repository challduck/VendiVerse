package shop.project.venver_order.order.service.dto.req;

import lombok.Builder;

public record ProductStockDecrementFeignRequest (Long productId, int quantity){
    @Builder
    public ProductStockDecrementFeignRequest{

    }
}
