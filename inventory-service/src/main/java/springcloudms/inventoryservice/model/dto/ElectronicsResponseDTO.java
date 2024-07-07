package springcloudms.inventoryservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import springcloudms.inventoryservice.model.enums.ElectronicCategoryEnum;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;
import springcloudms.inventoryservice.model.enums.WarehousesEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link springcloudms.inventoryservice.model.ElectronicsEntity}
 */
@Builder
public record ElectronicsResponseDTO(@NotNull String articleNo,
                                     @NotNull WarehousesEnum warehouse,
                                     @NotNull ProductTypeEnum productType,
                                     @NotNull @NotEmpty String title,
                                     @Min(0) Integer quantity,
                                     @PastOrPresent LocalDateTime lastStockUpdate,
                                     @NotNull @Min(0) BigDecimal purchasePrice,
                                     @NotNull ElectronicCategoryEnum category,
                                     String model,
                                     String characteristics,
                                     String description) implements Serializable {
}