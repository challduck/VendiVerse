package shop.project.vendiverse.wishlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.product.repository.ProductRepository;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.user.repository.UserRepository;
import shop.project.vendiverse.wishlist.dto.request.WishListCreateRequest;
import shop.project.vendiverse.wishlist.entity.WishList;
import shop.project.vendiverse.wishlist.repository.WishListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<WishList> getWishList() {
        return wishListRepository.findAll();
    }

    @Transactional
    public void addToWishList(WishListCreateRequest wishListRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
        Product product = productRepository.findById(wishListRequest.getId())
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));
        wishListRepository.save(WishList.builder()
                        .product(product)
                        .user(user)
                .build());
    }

    public void removeFromWishList(Long id) {
        wishListRepository.deleteById(id);
    }

}
