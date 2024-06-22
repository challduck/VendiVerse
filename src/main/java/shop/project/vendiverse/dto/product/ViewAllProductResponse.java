package shop.project.vendiverse.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ViewAllProductResponse {
    private final String productName;
    private final String productDescription;
    private final int productPrice;

    @Builder
    public ViewAllProductResponse(String productName, String productDescription, int productPrice){
        this.productDescription = productDescription;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
