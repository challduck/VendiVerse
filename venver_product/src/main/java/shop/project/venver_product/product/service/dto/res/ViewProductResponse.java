package shop.project.venver_product.product.service.dto.res;

import lombok.Builder;

public record ViewProductResponse(String productName, String productDescription, int productPrice) {
    @Builder
    public ViewProductResponse {
    }
}
