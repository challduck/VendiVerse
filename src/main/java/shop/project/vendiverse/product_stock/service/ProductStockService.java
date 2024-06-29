package shop.project.vendiverse.product_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.product.service.ProductService;
import shop.project.vendiverse.product_stock.dto.response.ProductStockResponse;
import shop.project.vendiverse.product_stock.entity.ProductStock;
import shop.project.vendiverse.product_stock.repository.ProductStockRepository;

@Service
@RequiredArgsConstructor
public class ProductStockService {

    private final ProductStockRepository productStockRepository;
    private final ProductService productService;

    public ProductStockResponse getStock(Long productId) {

        ProductStock productStock = productStockRepository.findByProductId(productId)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT_STOCK));

        return new ProductStockResponse(productStock.getStock());
    }

    @Transactional
    public void productStockDecrement(Product product, int quantity) {
        ProductStock productStock = productStockRepository.findByProductId(product.getId())
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));
        productStock.decrement(quantity);
        productStockRepository.save(productStock);
    }

}
