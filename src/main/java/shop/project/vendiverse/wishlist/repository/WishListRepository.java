package shop.project.vendiverse.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.vendiverse.wishlist.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

}
