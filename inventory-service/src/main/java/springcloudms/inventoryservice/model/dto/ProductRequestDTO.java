package springcloudms.inventoryservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;

@Builder
@Schema(description = "DTO-модель сущности товара")
public record ProductRequestDTO(
        @Schema(description = "Артикул продукта")
        String articleNo,
        @Schema(description = "Категория продукта")
        ProductTypeEnum productType,
        @Schema(description = "Количество на складе")
        Integer quantity
) {
}
