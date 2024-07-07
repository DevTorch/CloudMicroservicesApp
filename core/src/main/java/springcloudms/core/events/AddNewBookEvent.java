package springcloudms.core.events;

import springcloudms.core.enums.ProductTypeEnum;
import springcloudms.core.enums.WarehousesEnum;
import lombok.Builder;

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
