package springcloudms.inventoryservice.service;

import springcloudms.inventoryservice.model.ElectronicsEntity;
import springcloudms.inventoryservice.model.dto.ElectronicsResponseDTO;

import java.util.List;

public interface ElectronicsService extends AbstractService<ElectronicsResponseDTO, Long> {
    void saveAll(List<ElectronicsResponseDTO> electronics);
}
