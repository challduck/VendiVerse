package shop.project.venver_order.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.venver_order.payment.entity.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
//    List<>
    Optional<Payment> findByOrderId(Long orderId);
}
