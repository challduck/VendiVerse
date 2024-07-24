package shop.project.venver_order.order.controller.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_order.order.controller.dto.request.OrderExternalCartRequest;
import shop.project.venver_order.order.entity.Order;
import shop.project.venver_order.order.service.OrderService;
import shop.project.venver_order.order.service.dto.res.OrderListResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderExternalController {

    private final OrderService orderService;

    @PostMapping("/cart")
    public ResponseEntity<Order> placeOrderOfCart(@RequestHeader(name = "userId") Long userId, @RequestBody OrderExternalCartRequest orderRequest) {
        Order order = orderService.placeOrderOfCart(userId, orderRequest);
        return ResponseEntity.ok(order);
    }

//    @PostMapping("/cart")
//    public ResponseEntity<Order> placeOrderOfCart(@RequestBody OrderCartRequest orderRequest) {
//        Order order = orderService.placeOrderOfCart(orderRequest);
//        return ResponseEntity.ok(order);
//    }

    @GetMapping("/view")
    public ResponseEntity<List<OrderListResponse>> getAllOrder(@RequestHeader(name = "userId") Long userId) {
        List<OrderListResponse> response = orderService.getAllOrder(userId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@RequestHeader(name = "userId") Long userId,@PathVariable(name = "id") Long id) {
        orderService.cancelOrder(userId, id);
        return ResponseEntity.noContent().build();
    }

}