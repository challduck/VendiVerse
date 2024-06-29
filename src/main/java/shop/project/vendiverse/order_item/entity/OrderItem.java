package shop.project.vendiverse.order_item.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.order.entity.Order;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_order_item")
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;

    @Column
    private int price;

    @Column
    private int quantity;
}
