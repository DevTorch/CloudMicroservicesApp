package springcloudms.inventoryservice.events;

import lombok.Builder;
import springcloudms.inventoryservice.domain.ProductId;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;

@Builder
public record AddNewBookEvent(
        ProductId articleNo,
        ProductTypeEnum productType,
        String title,
        String author,
        String publisher,
        String isbnNo
) {
}
