package shop.project.venver_product.product.service.dto.res;

import lombok.Builder;

public record ProductInfoResponse (Long productId, int productPrice, int stock) {
    @Builder
    public ProductInfoResponse{

    }
}
