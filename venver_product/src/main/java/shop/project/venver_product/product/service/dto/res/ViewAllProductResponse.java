package shop.project.venver_product.product.service.dto.res;

import lombok.Builder;

public record ViewAllProductResponse(Long productId, String productName, String productDescription, int productPrice) {
    @Builder
    public ViewAllProductResponse {
    }
}
