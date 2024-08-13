package springcloudms.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.inventoryservice.domain.BaseInventoryProductEntity;
import springcloudms.inventoryservice.model.dto.ProductResponseDTO;
import springcloudms.inventoryservice.repository.InventoryRepository;
import springcloudms.inventoryservice.service.InventoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean isInStock(String articleNo, Integer quantity) {
        return inventoryRepository.isInStock(articleNo, quantity);
    }

    @Override
    @Transactional
    public Optional<ProductResponseDTO> findProductDTOByArticleNo(String articleNo) {
        return inventoryRepository.findProductDTOByArticleNo(articleNo);
    }

    @Override
    public List<ProductResponseDTO> findAll() {

        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        List<BaseInventoryProductEntity> all = inventoryRepository.findAll();
        log.info("{}", all);
        for (BaseInventoryProductEntity baseEntity : all) {
            ProductResponseDTO response = new ProductResponseDTO(
                    baseEntity.getArticleNo(),
                    baseEntity.getTitle(),
                    baseEntity.getProductType(),
                    baseEntity.getQuantity()
            );
            productResponseDTOS.add(response);
        }
        return productResponseDTOS;
    }
}
