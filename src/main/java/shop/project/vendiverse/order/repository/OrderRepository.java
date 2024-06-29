package shop.project.vendiverse.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.vendiverse.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
