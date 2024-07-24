package shop.project.venver_product.stock.controller.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.project.venver_product.facade.RedissonLockProductStockFacade;
import shop.project.venver_product.stock.controller.dto.req.ProductStockDecrementControllerRequest;
import shop.project.venver_product.stock.service.ProductStockService;
import shop.project.venver_product.stock.service.dto.req.ProductStockDecrementServiceRequest;

@RestController
@RequestMapping("/api/internal/v1/stock")
@RequiredArgsConstructor
public class ProductStockInternalController {
    private final ProductStockService productStockService;
    private final RedissonLockProductStockFacade redissonLockProductStockFacade;

    @PostMapping("/dec-stock")
    public ResponseEntity<Void> decrementProductStock(@RequestBody ProductStockDecrementControllerRequest request){
        ProductStockDecrementServiceRequest serviceRequest = new ProductStockDecrementServiceRequest(request);
        redissonLockProductStockFacade.decrease(serviceRequest);

        return ResponseEntity.ok().build();
    }

}
