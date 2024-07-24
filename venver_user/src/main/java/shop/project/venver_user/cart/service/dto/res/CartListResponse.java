package shop.project.venver_user.cart.service.dto.res;

import lombok.Builder;


public record CartListResponse(Long cartId, String productName, int quantity, int price, Long productId, int stock) {
    @Builder
    public CartListResponse {
    }
}
