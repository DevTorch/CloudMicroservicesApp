package springcloudms.inventoryservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;
import springcloudms.inventoryservice.model.enums.WarehousesEnum;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record BookResponseDTO(
        String articleNo,
        ProductTypeEnum productType,
        @NotNull String title,
        @NotNull String author,
        @NotNull String publisher,
        @NotNull String isbnNo,
        WarehousesEnum warehouse,
        @NotNull Integer quantity,
        @NotNull BigDecimal purchasePrice
) implements Serializable {

        public BookResponseDTO(String articleNo, ProductTypeEnum productType, String title, String author, String publisher, String isbnNo, WarehousesEnum warehouse, Integer quantity, BigDecimal purchasePrice) {
                this.articleNo = UUID.randomUUID().toString();
                this.productType = ProductTypeEnum.BOOKS;
                this.title = title;
                this.author = author;
                this.publisher = publisher;
                this.isbnNo = isbnNo;
                this.warehouse = warehouse;
                this.quantity = quantity;
                this.purchasePrice = purchasePrice;
        }
}

