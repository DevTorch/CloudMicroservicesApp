package springcloudms.notificationservice.events;

import lombok.Builder;
import springcloudms.notificationservice.enums.ProductTypeEnum;
import springcloudms.notificationservice.enums.WarehousesEnum;

import java.math.BigDecimal;

@Builder
public record AddNewBookEvent(
        String articleNo,
        ProductTypeEnum productType,
        String title,
        String author,
        String publisher,
        String isbnNo,
        WarehousesEnum warehouse,
        Integer quantity,
        BigDecimal purchasePrice
) {
}
