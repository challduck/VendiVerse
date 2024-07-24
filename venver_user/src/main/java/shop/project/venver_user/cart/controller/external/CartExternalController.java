package shop.project.venver_user.cart.controller.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_user.cart.controller.dto.req.CartCreateRequest;
import shop.project.venver_user.cart.entity.Cart;
import shop.project.venver_user.cart.service.CartService;
import shop.project.venver_user.cart.service.dto.res.CartListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartExternalController {
    private final CartService cartService;

    @GetMapping("/view")
    public ResponseEntity<List<CartListResponse>> getCart(@RequestHeader("userId") Long userId) {
        List<CartListResponse> cartList = cartService.getCartList(userId);
        return ResponseEntity.ok().body(cartList);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@RequestHeader("userId") Long userId, @RequestBody CartCreateRequest request) {

        cartService.cartAddProduct(userId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<Void> removeProductFromCart(@RequestHeader("userId") Long userId, @PathVariable("cartId") Long cartId) {
        cartService.cartRemoveProduct(userId, cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/increase/{cartId}")
    public ResponseEntity<Cart> increaseCartQuantity(@PathVariable(value = "cartId") Long cartId) {
        Cart updatedCart = cartService.cartQuantityIncrease(cartId);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/decrease/{cartId}")
    public ResponseEntity<Cart> decreaseCartQuantity(@PathVariable(value = "cartId")  Long cartId) {
        Cart updatedCart = cartService.cartQuantityDecrease(cartId);
        return ResponseEntity.ok(updatedCart);
    }
}
