package shop.project.vendiverse.product_stock.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.project.vendiverse.product.entity.Product;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    private int stock;

    public void decrement(int stock){
        this.stock = this.stock - stock;
    }
}
