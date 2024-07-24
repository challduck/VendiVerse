package shop.project.venver_order.order_item.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.project.venver_order.order.entity.Order;

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

    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;

    @Column
    private int price;

    @Column
    private int quantity;
}
