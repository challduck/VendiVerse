package shop.project.vendiverse.order.entity;


import jakarta.persistence.*;
import lombok.*;
import shop.project.vendiverse.payment.entity.Payment;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.util.OrderStatus;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private Payment payment;

    @Column
    private Long amount;

    @Column
    private LocalDateTime createAt;

}
