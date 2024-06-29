package shop.project.vendiverse.cart.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.vendiverse.cart.entity.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndProductId(Long cartId, Long productId);
    List<Cart> findByUserId(Long userId);
}
