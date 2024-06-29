package shop.project.vendiverse.product_stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.product_stock.dto.request.ProductStockRequest;
import shop.project.vendiverse.product_stock.dto.response.ProductStockResponse;
import shop.project.vendiverse.product_stock.service.ProductStockService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stock")
public class ProductStockController {

    private final ProductStockService productStockService;

    @GetMapping("/view")
    public ResponseEntity<ProductStockResponse> getStock(@RequestBody ProductStockRequest request){

        ProductStockResponse response = productStockService.getStock(request.productId());

        return ResponseEntity.ok().body(response);
    }

}
