package shop.project.venver_product.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_product.stock.entity.ProductStock;
import shop.project.venver_product.stock.repository.ProductStockRepository;
import shop.project.venver_product.stock.service.dto.req.ProductStockDecrementServiceRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStockService {
    private final ProductStockRepository productStockRepository;

    public int getProductStock(long productId){
        return productStockRepository.findByStockFromProductId(productId);
    }

    public List<ProductStock> getAllProductStock(List<Long> productIds){
        return productStockRepository.findByProductIdIn(productIds);
    }

    public Map<Long, Integer> getProductStocks(List<Long> productIds) {
        List<ProductStock> productStocks = productStockRepository.findByProductIdIn(productIds);
        return productStocks.stream().collect(Collectors.toMap(ps -> ps.getProduct().getId(), ProductStock::getStock));
    }

    @Transactional
    public void decrementStock(ProductStockDecrementServiceRequest request){
        ProductStock productStock = productStockRepository.findByProductId(request.productId());
        productStock.decrement(request.quantity());
        productStockRepository.save(productStock);
    }
}
