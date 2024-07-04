package springcloudms.inventoryservice.repository;

import cloudmicroservicesapp.core.enums.WarehousesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import springcloudms.inventoryservice.model.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {


    //    void saveAll(List<BookProduct> bookProducts);
    List<BookEntity> findAllByWarehouse(WarehousesEnum warehouse);

    Boolean existsByIdAndQuantity(Long id, Integer quantity);

    Optional<BookEntity> findByArticleNo(String articleNo);

    Boolean deleteByArticleNo(String articleNo);
}
