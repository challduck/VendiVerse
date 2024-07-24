package shop.project.venver_user.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_user.cart.controller.dto.req.CartCreateRequest;
import shop.project.venver_user.cart.entity.Cart;
import shop.project.venver_user.cart.service.dto.res.CartProductInfoResponse;
import shop.project.venver_user.feign.ProductFeignClient;
import shop.project.venver_user.cart.repositrory.CartRepository;
import shop.project.venver_user.cart.service.dto.req.CartProductInfoReq;
import shop.project.venver_user.cart.service.dto.res.CartListResponse;
import shop.project.venver_user.exception.ExceptionCode;
import shop.project.venver_user.exception.ExceptionResponse;
import shop.project.venver_user.user.entity.User;
import shop.project.venver_user.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductFeignClient productFeignClient;

    public List<CartProductInfoResponse> getProductInfoFromCartIds(List<Long> cartIds){
        List<Cart> carts = cartRepository.findByIdIn(cartIds);

        List<Long> productIds = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        List<CartProductInfoResponse> productInfoList = productFeignClient.getProductInfo(productIds);
        log.info("product id : {}", productInfoList);

        Map<Long, CartProductInfoResponse> productInfoMap = productInfoList.stream()
                .collect(Collectors.toMap(CartProductInfoResponse::productId, productInfo -> productInfo));

        return carts.stream()
                .map(cart -> {
                    CartProductInfoResponse productInfo = productInfoMap.get(cart.getProductId());
                    return new CartProductInfoResponse(
                            productInfo.productId(),
                            cart.getQuantity(),
                            productInfo.productPrice(),
                            productInfo.stock()
                    );
                })
                .collect(Collectors.toList());
    }

    public List<CartListResponse> getCartList(Long userId) {
        User user = userService.findUserById(userId);
        List<Cart> cartList = cartRepository.findByUserId(user.getId());

        List<Long> productIds = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        List<CartProductInfoReq> productInfos = productFeignClient.getCartProductInfo(productIds);

        Map<Long, CartProductInfoReq> productInfoMap = productInfos.stream()
                .collect(Collectors.toMap(CartProductInfoReq::productId, productInfo -> productInfo));

        return cartList.stream()
                .map(cart -> new CartListResponse(
                        cart.getId(),
                        productInfoMap.get(cart.getProductId()).productName(),
                        cart.getQuantity(),
                        productInfoMap.get(cart.getProductId()).productPrice(),
                        cart.getProductId(),
                        productInfoMap.get(cart.getProductId()).stock()))
                .collect(Collectors.toList());
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
        cart.decreaseQuantity(1);
        return cartRepository.save(cart);
    }

    @Transactional
    public void cartAddProduct(Long userId, CartCreateRequest request) {
        User user = userService.findUserById(userId);

        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(user.getId(), request.productId());

        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.increaseQuantity(1);
            cartRepository.save(cart);
        } else {
            cartRepository.save(Cart.builder()
                            .productId(request.productId())
                            .user(user)
                            .quantity(request.quantity())
                    .build());
        }
    }

    @Transactional
    public void cartRemoveProduct(Long userId, Long cartId) {
        log.info("request user id : {}", userId);
        log.info("request cart id : {}", cartId);
        User user = userService.findUserById(userId);

        Optional<Cart> existingCart = cartRepository.findById(cartId);
        log.info("is present : {}" , existingCart.isPresent());
        log.info("existingCart.get().getUser().getId() : {}", existingCart.get().getUser().getId());
        if (existingCart.isPresent() && existingCart.get().getUser().getId().equals(user.getId())) {
            cartRepository.delete(existingCart.get());
        } else {
            throw new IllegalStateException("해당 장바구니 항목이 사용자에게 속하지 않음");
        }
    }

}
