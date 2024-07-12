package springcloudms.inventoryservice.events;

import lombok.Builder;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;
import springcloudms.inventoryservice.model.enums.WarehousesEnum;

import java.math.BigDecimal;

@Builder
public record AddNewBookEvent(
        String articleNo,
        ProductTypeEnum productType,
        String title,
        String author,
        String publisher,
        String isbnNo,
        WarehousesEnum warehouse
) {
}
