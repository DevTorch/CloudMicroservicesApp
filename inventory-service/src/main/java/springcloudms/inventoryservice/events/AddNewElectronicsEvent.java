package springcloudms.inventoryservice.events;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import springcloudms.inventoryservice.model.enums.ElectronicCategoryEnum;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AddNewElectronicsEvent(
        @NotNull String articleNo,
        @NotNull @NotEmpty String title,
        String model,
        String characteristics,
        String description,
        @NotNull ProductTypeEnum productType,
        @NotNull ElectronicCategoryEnum category,
        @NotNull WarehousesEnum warehouse,
        @Min(0) Integer quantity,
        @PastOrPresent LocalDateTime lastStockUpdate,
        @NotNull @Min(0) BigDecimal purchasePrice
) {
}
