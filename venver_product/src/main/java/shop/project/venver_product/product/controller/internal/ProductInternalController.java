package shop.project.venver_product.product.controller.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_product.product.service.dto.res.ProductInfoResponse;
import shop.project.venver_product.product.service.dto.res.ViewProductInternalResponse;
import shop.project.venver_product.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/internal/v1/product")
@RequiredArgsConstructor
public class ProductInternalController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ViewProductInternalResponse> viewProduct(@PathVariable("id") Long id) {
        ViewProductInternalResponse response = productService.getProductById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/list")
    public ResponseEntity<List<ViewProductInternalResponse>> listProducts(@RequestBody List<Long> productId) {
        List<ViewProductInternalResponse> response = productService.getCartProductById(productId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/info")
    public List<ProductInfoResponse> productInfoResponses (@RequestBody List<Long> productIds){
        return productService.getProductInfoById(productIds);
    }
}
