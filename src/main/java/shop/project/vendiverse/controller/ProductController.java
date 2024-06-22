package shop.project.vendiverse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.project.vendiverse.domain.Product;
import shop.project.vendiverse.dto.product.ViewAllProductResponse;
import shop.project.vendiverse.dto.product.ViewProductResponse;
import shop.project.vendiverse.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all-view")
    public ResponseEntity<List<ViewAllProductResponse>> allViewProduct() {
        List<ViewAllProductResponse> responses = productService.findAll()
                .stream()
                .map(response -> ViewAllProductResponse.builder()
                        .productPrice(response.getPrice())
                        .productDescription(response.getDescription())
                        .productName(response.getName())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewProductResponse> viewProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        ViewProductResponse response = ViewProductResponse.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productPrice(product.getPrice())
                .productQuantity(product.getStock())
                .build();

        return ResponseEntity.ok().body(response);
    }

}
