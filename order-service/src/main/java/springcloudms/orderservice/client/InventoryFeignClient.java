package springcloudms.orderservice.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springcloudms.orderservice.dto.ProductRequestDTO;
import springcloudms.orderservice.exception.InventoryExistException;

@FeignClient(name = "inventory-service",
        fallbackFactory = InventoryFeignClient.InventoryFeignClientFallback.class,
//        url = "${inventory-service.host}",
        path = "/api/inventory")
public interface InventoryFeignClient {

    @GetMapping("/product/instock")
    Boolean isInStock(@RequestBody ProductRequestDTO productRequestDTO);

    @Component
    class InventoryFeignClientFallback implements FallbackFactory<FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements InventoryFeignClient {
        @Override
        public Boolean isInStock(ProductRequestDTO productRequestDTO) {
            String message = "Inventory is not available: %s, %d "
                    .formatted(productRequestDTO.articleNo(), productRequestDTO.quantity());
            throw new InventoryExistException(message + reason);
        }
    }
}
