package shop.project.venver_product.facade;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import shop.project.venver_product.stock.controller.dto.req.ProductStockDecrementControllerRequest;
import shop.project.venver_product.stock.service.ProductStockService;
import shop.project.venver_product.stock.service.dto.req.ProductStockDecrementServiceRequest;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockProductStockFacade {

    private final RedissonClient redissonClient;
    private final ProductStockService productStockService;

    public void decrease(ProductStockDecrementServiceRequest request) {
        RLock lock = redissonClient.getLock("product_stock_" + request.productId());

        try {
            boolean isLocked = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new RuntimeException("Failed to acquire lock for product " + request.productId());
            }

            productStockService.decrementStock(request);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock acquisition was interrupted", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}