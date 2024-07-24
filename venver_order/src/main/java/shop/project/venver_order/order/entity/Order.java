package shop.project.venver_order.order.entity;


import jakarta.persistence.*;
import lombok.*;
import shop.project.venver_order.payment.entity.Payment;
import shop.project.venver_order.util.OrderStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_order")
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private Long userId;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Payment payment;

    @Column
    private Long amount;

    @Column
    private LocalDateTime createAt;

    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }
}
