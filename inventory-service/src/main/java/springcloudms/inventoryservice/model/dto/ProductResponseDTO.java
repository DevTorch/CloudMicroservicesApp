package springcloudms.inventoryservice.model.dto;

import cloudmicroservicesapp.core.enums.ProductTypeEnum;

public record ProductResponseDTO(
        Long id,
        String articleNo,
        String title,
        ProductTypeEnum productType,
        Integer quantity
) {
}
