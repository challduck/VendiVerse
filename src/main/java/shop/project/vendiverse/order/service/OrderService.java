package shop.project.vendiverse.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.cart.entity.Cart;
import shop.project.vendiverse.cart.service.CartService;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.order.dto.request.OrderCartRequest;
import shop.project.vendiverse.order.entity.Order;
import shop.project.vendiverse.order.dto.request.OrderRequest;
import shop.project.vendiverse.order.repository.OrderRepository;
import shop.project.vendiverse.order_item.entity.OrderItem;
import shop.project.vendiverse.order_item.repository.OrderItemRepository;
import shop.project.vendiverse.order_item.service.OrderItemService;
import shop.project.vendiverse.payment.dto.request.PaymentAddRequest;
import shop.project.vendiverse.payment.service.PaymentService;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.product.service.ProductService;
import shop.project.vendiverse.product_stock.service.ProductStockService;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.user.service.UserService;
import shop.project.vendiverse.util.OrderStatus;
import shop.project.vendiverse.util.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final PaymentService paymentService;

    private final ProductService productService;
    private final ProductStockService productStockService;

    private final OrderItemService orderItemService;
    private final CartService cartService;

    @Transactional
    public Order createOrder(UserDetailsImpl userDetails, OrderRequest request) {
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);

        List<Long> cartId = request.cartId();
        long amount = 0;
        /*
            transaction 내 조회 요청 최소화
            List로 받아오는
         */
        // 1. 재고 확인
        for (Long l : cartId) {
            Cart cart = cartService.findCart(l);

            Product product = cart.getProduct();

            int stock = productStockService.getStock(product.getId()).stock();

            amount += (long) cart.getQuantity() * cart.getProduct().getPrice();

            if (cart.getQuantity() > stock){
                throw new ExceptionResponse(ExceptionCode.OUT_OF_STOCK);
            } else {
                // 7. Product stock 감소
                productStockService.productStockDecrement(cart.getProduct(), cart.getQuantity());
            }
        }

        // 2. Order 생성
        Order order = Order.builder()
                .user(user)
                .amount(amount)
                .createAt(LocalDateTime.now())
                .status(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order);

        // 3. Order Item 저장 (분리 할 것)
        for (Long l : cartId) {
            Cart cart = cartService.findCart(l);
            orderItemService.saveOrderItem(cart, order);
            // 6. Cart 삭제
//            cartService.cartRemoveProduct(userDetails, l);
        }

        // 4. payment method 저장
        paymentService.addPayment(new PaymentAddRequest(email, order.getId(), amount, PaymentMethod.valueOf(request.paymentMethod())));

        // 5. Delivery 저장

        return order;
    }


    public Order placeOrderOfCart(OrderCartRequest orderCartRequest) {
        return null;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public void cancelOrder(Long id) {
    }

    public void returnOrder(Long id) {
    }

}
