package shop.project.vendiverse.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.project.vendiverse.cart.entity.Cart;
import shop.project.vendiverse.util.Role;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String detailAddress;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles;

    @Column
    private boolean email_verified = false;

    @OneToMany
    private List<Cart> cart;

    @Builder
    public User(String email, String password, String name, String phoneNumber, String address, boolean email_verified, String detailAddress, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.email_verified = email_verified;
        this.roles = roles;
    }

    public User passwordUpdate(String password) {
        this.password = password;
        return this;
    }

    public void userInfoUpdate(String phoneNumber, String address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
