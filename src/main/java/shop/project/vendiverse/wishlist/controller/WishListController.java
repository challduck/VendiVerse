package shop.project.vendiverse.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.wishlist.dto.request.WishListCreateRequest;
import shop.project.vendiverse.wishlist.entity.WishList;
import shop.project.vendiverse.wishlist.service.WishListService;

import java.util.List;

@RestController
@RequestMapping("/api/wish-list")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;

    @GetMapping("/wishlist")
    public ResponseEntity<List<WishList>> getWishList() {
        List<WishList> wishList = wishListService.getWishList();
        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/wishlist")
    public ResponseEntity<String> addToWishList(@RequestBody WishListCreateRequest wishListRequest) {
        try {
            wishListService.addToWishList(wishListRequest);
            return ResponseEntity.ok().body("Wish List 저장 성공");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wish List 저장 실패");
        }
    }

    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<Void> removeFromWishList(@PathVariable Long id) {
        wishListService.removeFromWishList(id);
        return ResponseEntity.noContent().build();
    }

}
