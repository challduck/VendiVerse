package shop.project.venver_user.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean emailVerified;

//    @OneToMany
//    private List<Cart> carts;

    public User(Long id, String email, String password, String name, String phoneNumber, String address, String detailAddress, Role role, boolean emailVerified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.emailVerified = emailVerified;
        this.role = role;
//        this.carts = cart;
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }

    public void userInfoUpdate(String phoneNumber, String address, String detailAddress) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
    }

}
