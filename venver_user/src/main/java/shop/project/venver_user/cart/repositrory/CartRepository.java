package shop.project.venver_user.cart.repositrory;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.project.venver_user.cart.entity.Cart;
import shop.project.venver_user.cart.service.dto.res.CartProductInfoResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndProductId(Long cartId, Long productId);

    List<Cart> findByUserId(Long userId);

    List<Cart> findByIdIn(List<Long> cartIds);

}
