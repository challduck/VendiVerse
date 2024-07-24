package shop.project.venver_order.order.service.dto.req;

public record CartProductInfoReq(Long productId, int quantity , int productPrice, int stock) {
}
