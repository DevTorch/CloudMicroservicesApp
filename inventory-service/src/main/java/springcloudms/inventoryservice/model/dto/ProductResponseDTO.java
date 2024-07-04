package springcloudms.inventoryservice.model.dto;

public record ProductResponseDTO(
        Long id,
        String articleNo,
        String title,
        ProductTypeEnum productType,
        Integer quantity
) {
}
