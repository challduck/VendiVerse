package shop.project.vendiverse.delivery.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.project.vendiverse.order.entity.Order;
import shop.project.vendiverse.util.DeliveryStatus;

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
