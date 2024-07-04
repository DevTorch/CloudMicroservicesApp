package springcloudms.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.productservice.dto.ProductRequestDTO;
import springcloudms.productservice.dto.ProductResponseDTO;
import springcloudms.productservice.model.Product;
import springcloudms.productservice.repository.ProductRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = Product.builder()
                .title(request.name())
                .category(request.category())
                .description(request.description())
                .price(request.price())
                .build();
        productRepository.save(product);

        log.info("Product created successfully: {}", product);

        return ProductResponseDTO.builder()
                .id(product.getId())
                .category(product.getCategory())
                .name(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getTitle())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                .toList();
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
