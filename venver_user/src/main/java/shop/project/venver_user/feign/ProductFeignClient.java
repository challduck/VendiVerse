package shop.project.venver_user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_user.cart.service.dto.req.CartProductInfoReq;
import shop.project.venver_user.cart.service.dto.res.CartProductInfoResponse;

import java.util.List;

@FeignClient(name = "venver-product")
public interface ProductFeignClient {
    @PostMapping("/api/internal/v1/product/list")
    List<CartProductInfoReq> getCartProductInfo(@RequestBody List<Long> productIds);

    @PostMapping("/api/internal/v1/product/info")
    List<CartProductInfoResponse> getProductInfo(@RequestBody List<Long> productId);

}
