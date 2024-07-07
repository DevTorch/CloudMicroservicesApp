package springcloudms.inventoryservice.model.dto;

import lombok.Builder;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;

@Builder
public record ProductResponseDTO(
        String articleNo,
        String title,
        ProductTypeEnum productType,
        Integer quantity
) {
}
