package shop.project.venver_order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.project.venver_order.order.service.dto.req.ProductInfoReq;
import shop.project.venver_order.order.service.dto.req.ProductStockDecrementFeignRequest;

import java.util.List;

@FeignClient(name = "venver-product")
public interface ProductFeignClient {

//    @PostMapping("/api/internal/v1/product/list")
//    List<CartProductInfoReq> getCartProductInfo(@RequestBody List<Long> id);

//    @PostMapping("/api/internal/v1/product/get-stock")
//    Long getStock(@RequestBody Long productId);

    @PostMapping("/api/internal/v1/stock/dec-stock")
    Void decStock(@RequestBody ProductStockDecrementFeignRequest request);

    @PostMapping("/api/internal/v1/product/list")
    List<ProductInfoReq> getProductInfo(@RequestBody List<Long> productIds);

}
