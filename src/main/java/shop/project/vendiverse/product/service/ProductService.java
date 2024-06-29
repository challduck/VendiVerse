package shop.project.vendiverse.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.project.vendiverse.product.entity.Product;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.product.repository.ProductRepository;

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
