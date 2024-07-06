package shop.project.venver_product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ProductStock {
    @Id
    @GeneratedValue
    private Long id;


}
