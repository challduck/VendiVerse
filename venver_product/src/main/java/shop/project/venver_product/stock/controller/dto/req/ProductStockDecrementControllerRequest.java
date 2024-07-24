package shop.project.venver_product.stock.controller.dto.req;

public record ProductStockDecrementControllerRequest (Long productId, int quantity){
}
