package shop.project.venver_product.stock.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.project.venver_product.stock.entity.ProductStock;

import java.util.List;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    @Query("SELECT ps.stock FROM ProductStock ps WHERE ps.product.id = :productId")
    Integer findByStockFromProductId(@Param("productId") long productId);

    List<ProductStock> findByProductIdIn(List<Long> productIds);

    ProductStock findByProductId(Long productId);
}
