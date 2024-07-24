package shop.project.venver_product.product.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.project.venver_product.stock.entity.ProductStock;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductStock productStock;

}