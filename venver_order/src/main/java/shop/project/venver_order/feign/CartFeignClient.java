package shop.project.venver_order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.project.venver_order.order.service.dto.req.CartProductInfoReq;

import java.util.List;

@FeignClient(name = "venver-user")
public interface CartFeignClient {
    @PostMapping("/api/internal/v1/cart/del")
    Void delCart(@RequestHeader("userId") Long userId, @RequestBody Long cartId);

    @PostMapping("/api/internal/v1/cart/info")
    List<CartProductInfoReq> getProductInfoFromCartId (@RequestBody List<Long> cartIds);

}
