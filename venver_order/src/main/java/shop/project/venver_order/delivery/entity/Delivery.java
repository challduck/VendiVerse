package shop.project.venver_order.delivery.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.project.venver_order.order.entity.Order;
import shop.project.venver_order.util.DeliveryStatus;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
