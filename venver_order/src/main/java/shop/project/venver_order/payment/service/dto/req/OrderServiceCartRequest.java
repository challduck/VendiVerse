package shop.project.venver_order.payment.service.dto.req;

import java.util.List;

public record OrderServiceCartRequest (List<Long> cartId, String paymentMethod) {
}
