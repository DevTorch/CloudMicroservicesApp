package springcloudms.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import springcloudms.orderservice.model.ProductTypeEnum;

@Builder
@Schema(description = "DTO-модель продукта")
public record ProductRequestDTO(
        @Schema(description = "Артикул продукта")
        String articleNo,
        @Schema(description = "Категория продукта")
        ProductTypeEnum productType,
        @Schema(description = "Количество на складе")
        Integer quantity
) {
}
