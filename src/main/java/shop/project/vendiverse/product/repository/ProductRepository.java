package shop.project.vendiverse.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.vendiverse.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findAllByOrderById(Long id);
        List<Product> findAllByOrderByIdDesc();
}
