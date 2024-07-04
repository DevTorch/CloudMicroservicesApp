package springcloudms.productservice.dto;

import lombok.Builder;
import springcloudms.productservice.model.ProductCategoryEnum;

import java.math.BigDecimal;

@Builder
public record ProductResponseDTO(
        Long id,
        ProductCategoryEnum category,
        String name,
        String description,
        BigDecimal price
) {
}