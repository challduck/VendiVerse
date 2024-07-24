package shop.project.venver_user.shipping_address.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.project.venver_user.user.entity.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String address;

    private String detailAddress;

    private boolean isPrimary;

    public void updateAddress(String address, String detailAddress){
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public void updatePrimaryAddress(boolean isPrimary){
        this.isPrimary = isPrimary;
    }
}
