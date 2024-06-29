package shop.project.vendiverse.order_item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.cart.entity.Cart;
import shop.project.vendiverse.order.entity.Order;
import shop.project.vendiverse.order_item.entity.OrderItem;
import shop.project.vendiverse.order_item.repository.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void saveOrderItem(Cart cart, Order order){
        OrderItem orderItem = OrderItem.builder()
                .product(cart.getProduct())
                .order(order)
                .price(cart.getProduct().getPrice())
                .quantity(cart.getQuantity())
                .build();
        orderItemRepository.save(orderItem);
    }
}
