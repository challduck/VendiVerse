package shop.project.vendiverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.project.vendiverse.domain.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

}
