package shop.project.venver_product.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.project.venver_product.product.entity.Product;
import shop.project.venver_product.exception.ExceptionCode;
import shop.project.venver_product.exception.ExceptionResponse;
import shop.project.venver_product.product.repository.ProductRepository;
import shop.project.venver_product.product.service.dto.res.ProductInfoResponse;
import shop.project.venver_product.product.service.dto.res.ViewAllProductResponse;
import shop.project.venver_product.product.service.dto.res.ViewProductInternalResponse;
import shop.project.venver_product.product.service.dto.res.ViewProductResponse;
import shop.project.venver_product.stock.entity.ProductStock;
import shop.project.venver_product.stock.service.ProductStockService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStockService productStockService;

    public List<ViewAllProductResponse> findAll() {

        return productRepository.findAllByOrderByIdDesc().stream()
                .map(response -> ViewAllProductResponse.builder()
                        .productId(response.getId())
                        .productPrice(response.getPrice())
                        .productDescription(response.getDescription())
                        .productName(response.getName())
                        .build())
                .toList();
    }

    public ViewProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));

        return ViewProductResponse.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productPrice(product.getPrice())
                .build();
    }

    public ViewProductInternalResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));

        return ViewProductInternalResponse.builder()
                .productId(id)
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productPrice(product.getPrice())
                .stock(productStockService.getProductStock(id))
                .build();
    }

    public List<ViewProductInternalResponse> getCartProductById(List<Long> productId) {
        List<Product> products = productRepository.findByIdIn(productId);

        List<ProductStock> productStocks = productStockService.getAllProductStock(productId);

        Map<Product, ProductStock> productStockMap = productStocks.stream()
                .collect(Collectors.toMap(ProductStock::getProduct, stock -> stock));

        return products.stream()
                .map(product -> {
                    ProductStock stock = productStockMap.get(product);
                    return ViewProductInternalResponse.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .productDescription(product.getDescription())
                            .productPrice(product.getPrice())
                            .stock(stock != null ? stock.getStock() : 0) // 재고 정보를 설정
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<ProductInfoResponse> getProductInfoById(List<Long> productIds) {
        List<Product> products = productRepository.findByIdIn(productIds);
        return products.stream()
                .map(product -> ProductInfoResponse.builder()
                        .productId(product.getId())
                        .productPrice(product.getPrice())
                        .stock(productStockService.getProductStock(product.getId()))
                        .build()).toList();
    }
}
