package shop.project.venver_user.cart.service.dto.req;

public record CartProductInfoReq(Long productId, String productName, String productDescription, int productPrice, int stock) {
}
