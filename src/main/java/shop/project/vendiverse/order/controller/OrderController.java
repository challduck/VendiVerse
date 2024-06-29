package shop.project.vendiverse.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.order.dto.request.OrderCartRequest;
import shop.project.vendiverse.order.entity.Order;
import shop.project.vendiverse.order.dto.request.OrderRequest;
import shop.project.vendiverse.order.service.OrderService;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.wishlist.service.WishListService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final WishListService wishListService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(userDetails, orderRequest);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/cart")
    public ResponseEntity<Order> placeOrderOfCart(@RequestBody OrderCartRequest orderRequest) {
        Order order = orderService.placeOrderOfCart(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnOrder(@PathVariable Long id) {
        orderService.returnOrder(id);
        return ResponseEntity.noContent().build();
    }
}