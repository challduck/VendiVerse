package shop.project.venver_user.cart.controller.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_user.cart.service.CartService;
import shop.project.venver_user.cart.service.dto.res.CartProductInfoResponse;

import java.util.List;

@RestController
@RequestMapping("/api/internal/v1/cart")
@RequiredArgsConstructor
public class CartInternalController {
    private final CartService cartService;

    @PostMapping("/info")
    public List<CartProductInfoResponse> getProductInfoFromCartIds (@RequestBody List<Long> cartIds) {
        return cartService.getProductInfoFromCartIds(cartIds);
    }

    @PostMapping("/del")
    public ResponseEntity<Void> deleteCart(@RequestHeader("userId") Long userId, @RequestBody Long cartId){
        cartService.cartRemoveProduct(userId, cartId);
        return ResponseEntity.ok().build();
    }
}
