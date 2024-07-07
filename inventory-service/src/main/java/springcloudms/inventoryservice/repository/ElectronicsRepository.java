package springcloudms.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.inventoryservice.model.ElectronicsEntity;

@Repository
public interface ElectronicsRepository extends JpaRepository<ElectronicsEntity, Long> {
}