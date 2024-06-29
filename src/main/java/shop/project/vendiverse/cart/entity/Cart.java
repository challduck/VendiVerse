package shop.project.vendiverse.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.user.entity.User;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private int quantity;

    @Builder
    public Cart(Product product, User user, int quantity) {
        this.product = product;
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
