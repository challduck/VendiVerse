package shop.project.vendiverse.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import shop.project.vendiverse.product.entity.Product;


@Getter
public class CartListResponse{
    private final Long cartId;
    private final String productName;
    private final int quantity;
    private final int price;
    private final Long productId;

    @Builder
    public CartListResponse(Long cartId, String productName, int quantity, int price, Long productId){
        this.cartId = cartId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }
}
