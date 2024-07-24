package shop.project.venver_user.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.project.venver_user.user.entity.User;

import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private int quantity;

    @Builder
    public Cart(Long productId, User user, int quantity) {
        this.productId = productId;
        this.user = user;
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }
    public void decreaseQuantity(int quantity){
        this.quantity -= quantity ;
    }

}
