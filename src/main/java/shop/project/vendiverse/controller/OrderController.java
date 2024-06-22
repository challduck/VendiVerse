package shop.project.vendiverse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.domain.Order;
import shop.project.vendiverse.service.OrderService;
import shop.project.vendiverse.service.WishListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final WishListService wishListService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/wishlist")
    public ResponseEntity<List<WishList>> getWishList() {
        List<WishList> wishList = wishListService.getWishList();
        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/wishlist")
    public ResponseEntity<WishList> addToWishList(@RequestBody WishListRequest wishListRequest) {
        WishList wishList = wishListService.addToWishList(wishListRequest);
        return ResponseEntity.ok(wishList);
    }

    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<Void> removeFromWishList(@PathVariable Long id) {
        wishListService.removeFromWishList(id);
        return ResponseEntity.noContent().build();
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