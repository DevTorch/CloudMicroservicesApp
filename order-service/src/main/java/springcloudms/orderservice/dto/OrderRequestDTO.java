package springcloudms.orderservice.dto;

import lombok.Builder;
import springcloudms.orderservice.model.ProductTypeEnum;

import java.math.BigDecimal;

@Builder
public record OrderRequestDTO(
        Long id,
        String orderNumber,
        String articleNo,
        ProductTypeEnum productType,
        BigDecimal price,
        Integer quantity
) {
}
