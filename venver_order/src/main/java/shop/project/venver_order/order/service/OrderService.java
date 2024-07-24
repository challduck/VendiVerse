package shop.project.venver_order.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_order.exception.ExceptionCode;
import shop.project.venver_order.exception.ExceptionResponse;
import shop.project.venver_order.feign.CartFeignClient;
import shop.project.venver_order.feign.ProductFeignClient;
import shop.project.venver_order.order.controller.dto.request.OrderExternalCartRequest;
import shop.project.venver_order.order.entity.Order;
import shop.project.venver_order.order.repository.OrderRepository;
import shop.project.venver_order.order.service.dto.req.CartProductInfoReq;
import shop.project.venver_order.order.service.dto.req.ProductInfoReq;
import shop.project.venver_order.order.service.dto.req.ProductStockDecrementFeignRequest;
import shop.project.venver_order.order.service.dto.res.OrderListResponse;
import shop.project.venver_order.order_item.entity.OrderItem;
import shop.project.venver_order.order_item.service.OrderItemService;
import shop.project.venver_order.payment.controller.dto.request.PaymentAddRequest;
import shop.project.venver_order.payment.entity.Payment;
import shop.project.venver_order.payment.service.PaymentService;
import shop.project.venver_order.util.OrderStatus;
import shop.project.venver_order.util.PaymentMethod;
import shop.project.venver_order.util.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final CartFeignClient cartFeignClient;
    private final ProductFeignClient productFeignClient;
    private final OrderItemService orderItemService;

    @Transactional
    public Order placeOrderOfCart(Long userId, OrderExternalCartRequest request) {
        List<Long> cartIds = request.cartId();
        long amount = 0;

        /*
            transaction 내 조회 요청 최소화
            반복문 query -> 한번에 조회하는 query로 변경
         */

        // 1. 재고 확인
        List<CartProductInfoReq> reqs = cartFeignClient.getProductInfoFromCartId(cartIds);

        // 2. 총 주문 금액 계산
        for (CartProductInfoReq req : reqs) {
            if (req.quantity() > req.stock()) {
                throw new ExceptionResponse(ExceptionCode.OUT_OF_STOCK);
            }
            amount += (long) req.quantity() * req.productPrice();
        }

        // 3. Order 생성
        Order order = Order.builder()
                .userId(userId)
                .amount(amount)
                .createAt(LocalDateTime.now())
                .status(OrderStatus.ORDERED)
                .build();

        orderRepository.save(order);

        // 3. Order Item 저장 (분리 할 것)
        List<OrderItem> orderItems = reqs.stream().map(req -> {
            productFeignClient.decStock(ProductStockDecrementFeignRequest.builder()
                    .productId(req.productId())
                    .quantity(req.quantity())
                    .build());

            return OrderItem.builder()
                    .productId(req.productId())
                    .price(req.productPrice())
                    .quantity(req.quantity())
                    .order(order)
                    .build();
        }).toList();

        orderItemService.saveAllOrderItems(orderItems);

        // 4. payment method 저장
        paymentService.addPayment(userId ,order.getId(), amount, PaymentMethod.valueOf(request.paymentMethod()));

        // 5. Delivery 저장


        // 6. 모든 절차를 마치고 List<cartId> 항목 삭제 feign req
        for (Long id : cartIds) {
            cartFeignClient.delCart(userId, id);
        }

        return order;
    }


//    public Order placeOrderOfProduct(OrderCartRequest orderCartRequest) {
//        return null;
//    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Transactional
    public void cancelOrder(Long userId, Long id) {
        Order order = getOrderById(id);

        if (!userId.equals(order.getUserId())) {
            throw new ExceptionResponse(ExceptionCode.BAD_REQUEST);
        }

        order.updateOrderStatus(OrderStatus.CANCELLED);
        paymentService.updatePaymentStatus(order.getId(), PaymentStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<OrderListResponse> getAllOrder(Long userId) {
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        List<Long> productIds = orders.stream()
                .flatMap(order -> orderItemService.getOrderItemList(order).stream())
                .map(OrderItem::getProductId)
                .distinct()
                .toList();

        List<ProductInfoReq> productInfoList = productFeignClient.getProductInfo(productIds);

        Map<Long, String> productInfoMap = productInfoList.stream()
                .collect(Collectors.toMap(ProductInfoReq::productId, ProductInfoReq::productName));

        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemService.getOrderItemList(order);
                    long amount = orderItems.stream().mapToLong(OrderItem::getPrice).sum();
                    long quantity = orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
                    String productName = orderItems.stream()
                            .map(orderItem -> productInfoMap.get(orderItem.getProductId()))
                            .findFirst()
                            .orElse("Unknown");

                    return OrderListResponse.builder()
                            .productName(productName)
                            .amount(amount)
                            .status(order.getStatus())
                            .quantity(quantity)
                            .orderedAt(order.getCreateAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
