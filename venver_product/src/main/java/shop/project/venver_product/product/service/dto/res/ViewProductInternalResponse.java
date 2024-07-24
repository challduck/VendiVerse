package shop.project.venver_product.product.service.dto.res;

import lombok.Builder;

public record ViewProductInternalResponse (Long productId, String productName, String productDescription, int productPrice, int stock){
    @Builder
    public ViewProductInternalResponse{

    }
}
