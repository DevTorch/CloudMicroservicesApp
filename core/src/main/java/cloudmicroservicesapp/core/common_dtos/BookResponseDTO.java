package cloudmicroservicesapp.core.common_dtos;

import cloudmicroservicesapp.core.enums.ProductTypeEnum;
import cloudmicroservicesapp.core.enums.WarehousesEnum;


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
