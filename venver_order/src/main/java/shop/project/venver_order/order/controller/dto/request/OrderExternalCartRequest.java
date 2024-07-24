package shop.project.venver_order.order.controller.dto.request;

import java.util.List;

public record OrderExternalCartRequest(List<Long> cartId,  String paymentMethod){
}
