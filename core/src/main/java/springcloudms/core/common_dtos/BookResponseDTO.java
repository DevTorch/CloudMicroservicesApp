package springcloudms.core.common_dtos;

import springcloudms.core.enums.ProductTypeEnum;
import springcloudms.core.enums.WarehousesEnum;


import java.io.Serializable;
import java.math.BigDecimal;

public record BookResponseDTO(
        String articleNo,
        ProductTypeEnum productType,
        String title,
        String author,
        String publisher,
        String isbnNo,
        WarehousesEnum warehouse,
        Integer quantity,
        BigDecimal purchasePrice
) implements Serializable {
}
