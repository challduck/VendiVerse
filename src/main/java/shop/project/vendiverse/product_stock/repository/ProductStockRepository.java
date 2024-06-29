package shop.project.vendiverse.product_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.product_stock.dto.response.ProductStockResponse;
import shop.project.vendiverse.product_stock.entity.ProductStock;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    Optional<ProductStock> findByProductId(Long productId);
}
