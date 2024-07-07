package springcloudms.inventoryservice.service;

import springcloudms.inventoryservice.model.dto.ProductRequestDTO;
import springcloudms.inventoryservice.model.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Boolean isInStock(String articleNo, Integer quantity);

    Optional<ProductResponseDTO> findProductDTOByArticleNo(String articleNo);

    List<ProductResponseDTO> findAll();
}
