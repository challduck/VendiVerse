package shop.project.vendiverse.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ViewProductResponse {
    private final String productName;
    private final String productDescription;
    private final int productPrice;
    private final int productQuantity;

    @Builder
    public ViewProductResponse(String productName, String productDescription, int productPrice, int productQuantity){
        this.productDescription = productDescription;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
}
