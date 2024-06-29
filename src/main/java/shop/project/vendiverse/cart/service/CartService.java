package shop.project.vendiverse.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.cart.dto.request.CartCreateRequest;
import shop.project.vendiverse.cart.entity.Cart;
import shop.project.vendiverse.cart.repositrory.CartRepository;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.product.service.ProductService;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final UserService userService;

    public Cart findCart(Long cartId){
        return cartRepository.findById(cartId)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_CART));
    }

    public List<Cart> getCartList(UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);
        return cartRepository.findByUserId(user.getId());
    }

    @Transactional
    public Cart cartQuantityIncrease(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ExceptionResponse(ExceptionCode.NOT_FOUND_CART));
        cart.increaseQuantity(1);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart cartQuantityDecrease(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ExceptionResponse(ExceptionCode.NOT_FOUND_CART));
        cart.decreaseQuantity(-1);
        return cartRepository.save(cart);
    }

    @Transactional
    public void cartAddProduct(UserDetailsImpl userDetails, CartCreateRequest request) {
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);

        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(user.getId(), request.productId());
        Product product = productService.findById(request.productId());

        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.increaseQuantity(1);
            cartRepository.save(cart);
        } else {
            cartRepository.save(Cart.builder()
                            .product(product)
                            .user(user)
                            .quantity(request.quantity())
                    .build());
        }
    }

    @Transactional
    public void cartRemoveProduct(UserDetailsImpl userDetails, Long cartId) {
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);

        Optional<Cart> existingCart = cartRepository.findById(cartId);

        if (existingCart.isPresent() && existingCart.get().getUser().getId().equals(user.getId())) {
            cartRepository.delete(existingCart.get());
        } else {
            throw new IllegalStateException("해당 장바구니 항목이 사용자에게 속하지 않음");
        }
    }


}
