package shop.project.venver_order.order_item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_order.order.entity.Order;
import shop.project.venver_order.order_item.entity.OrderItem;
import shop.project.venver_order.order_item.repository.OrderItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void saveOrderItem(Long productId,int productPrice, int quantity, Order order){

        OrderItem orderItem = OrderItem.builder()
                .productId(productId)
                .order(order)
                .price(productPrice)
                .quantity(quantity)
                .build();

        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrderItemList(Order order){
        return orderItemRepository.findAllByOrderId(order.getId());
    }

    public void saveAllOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
