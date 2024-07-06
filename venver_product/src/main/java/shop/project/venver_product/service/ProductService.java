package shop.project.venver_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.project.venver_product.entity.Product;
import shop.project.venver_product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAllByOrderByIdDesc();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));
    }

}
