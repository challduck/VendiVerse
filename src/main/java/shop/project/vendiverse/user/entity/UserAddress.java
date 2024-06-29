package shop.project.vendiverse.user.entity;

import jakarta.persistence.*;

@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false, name = "user_addr_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column
    private String address;

    @Column
    private String detailAddress;
}
