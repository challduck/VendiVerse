package shop.project.vendiverse.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.project.vendiverse.order.entity.Order;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.util.PaymentMethod;
import shop.project.vendiverse.util.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String paymentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Payment(User user, Order order, Long amount, PaymentMethod paymentMethod, String paymentStatus, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.user = user;
        this.order = order;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Payment paymentStatusUpdate(PaymentStatus newStatus) {
        this.paymentStatus = newStatus.name();
        this.updatedAt = LocalDateTime.now();
        return this;
    }


}
