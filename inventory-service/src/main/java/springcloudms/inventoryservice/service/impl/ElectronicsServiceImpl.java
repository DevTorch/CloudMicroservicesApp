package springcloudms.inventoryservice.service.impl;

import org.springframework.stereotype.Service;
import springcloudms.inventoryservice.model.ElectronicsEntity;
import springcloudms.inventoryservice.model.dto.ElectronicsResponseDTO;
import springcloudms.inventoryservice.service.ElectronicsService;

import java.util.List;
import java.util.Optional;

@Service
public class ElectronicsServiceImpl implements ElectronicsService {
    @Override
    public Optional<ElectronicsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ElectronicsEntity save(ElectronicsEntity entity) {
        return null;
    }

    @Override
    public ElectronicsEntity update(ElectronicsEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void saveAll(List<ElectronicsResponseDTO> electronics) {

    }
}
