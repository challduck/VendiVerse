package shop.project.venver_order.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.project.venver_order.order.entity.Order;
import shop.project.venver_order.util.PaymentMethod;
import shop.project.venver_order.util.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String paymentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Payment paymentStatusUpdate(PaymentStatus newStatus) {
        this.paymentStatus = newStatus.name();
        this.updatedAt = LocalDateTime.now();
        return this;
    }


}
