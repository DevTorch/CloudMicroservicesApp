package springcloudms.productservice.events;

import lombok.Builder;
import springcloudms.notificationservice.enums.ElectronicCategoryEnum;
import springcloudms.notificationservice.enums.ProductTypeEnum;
import springcloudms.notificationservice.enums.WarehousesEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AddNewElectronicsEvent(
        String articleNo,
        String title,
        String model,
        String characteristics,
        String description,
        ElectronicCategoryEnum category
) {
}
