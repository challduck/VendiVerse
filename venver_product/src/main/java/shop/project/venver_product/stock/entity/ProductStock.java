package shop.project.venver_product.stock.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.project.venver_product.product.entity.Product;

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
    @JoinColumn(name = "product_id")
    private Product product;

    private int stock;

    public void decrement(int stock){
        this.stock = this.stock - stock;
    }
}
