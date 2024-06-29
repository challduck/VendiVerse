package shop.project.vendiverse.shipping_address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.vendiverse.shipping_address.entity.ShippingAddress;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findByUserId(Long userId);
}
