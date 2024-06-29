package shop.project.vendiverse.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.cart.dto.request.CartCreateRequest;
import shop.project.vendiverse.cart.dto.response.CartListResponse;
import shop.project.vendiverse.cart.entity.Cart;
import shop.project.vendiverse.cart.service.CartService;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.security.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartListResponse>> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CartListResponse> cartList = cartService.getCartList(userDetails).stream()
                .map(response -> new CartListResponse(
                        response.getId(),
                        response.getProduct().getName(),
                        response.getProduct().getPrice(),
                        response.getQuantity(),
                        response.getProduct().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartList);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartCreateRequest request) {
        cartService.cartAddProduct(userDetails,request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{cartId}")
    public ResponseEntity<Void> removeProductFromCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("cartId") Long cartId) {
        cartService.cartRemoveProduct(userDetails, cartId);
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
