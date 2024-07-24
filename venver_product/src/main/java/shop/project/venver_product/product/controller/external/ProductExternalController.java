package shop.project.venver_product.product.controller.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.project.venver_product.exception.ExceptionResponse;
import shop.project.venver_product.product.service.ProductService;
import shop.project.venver_product.product.service.dto.res.ViewAllProductResponse;
import shop.project.venver_product.product.service.dto.res.ViewProductResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductExternalController {

    private final ProductService productService;

    @GetMapping("/all-view")
    public ResponseEntity<List<ViewAllProductResponse>> allViewProduct() {
        List<ViewAllProductResponse> responses = productService.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewProduct(@PathVariable("id") Long id) {
        try {
            ViewProductResponse response = productService.findById(id);
            return ResponseEntity.ok().body(response);
        } catch (ExceptionResponse e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
