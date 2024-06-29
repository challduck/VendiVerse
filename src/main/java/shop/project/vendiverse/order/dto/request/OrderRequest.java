package shop.project.vendiverse.order.dto.request;

import java.util.List;

public record OrderRequest (List<Long> cartId, String paymentMethod){
}
