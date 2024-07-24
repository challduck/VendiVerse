package shop.project.venver_product.stock.service.dto.req;

import shop.project.venver_product.stock.controller.dto.req.ProductStockDecrementControllerRequest;

public record ProductStockDecrementServiceRequest(Long productId, int quantity){

    public ProductStockDecrementServiceRequest(ProductStockDecrementControllerRequest request){
        this(
                request.productId(),
                request.quantity()
        );
    }
}
