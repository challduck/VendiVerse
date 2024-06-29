package shop.project.vendiverse.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.project.vendiverse.cart.entity.Cart;
import shop.project.vendiverse.wishlist.entity.WishList;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int price;

    @Column
    private int stock;

    @OneToMany
    private List<WishList> wishlists;

//    @OneToMany
//    private List<OrderProduct> orderProducts;

//    @OneToMany(mappedBy = "product")
    @OneToMany
    private List<Cart> carts;

//    @OneToOne
//    private ProductStock productStock;

}
