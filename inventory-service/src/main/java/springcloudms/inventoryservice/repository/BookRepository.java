package springcloudms.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springcloudms.inventoryservice.model.BookEntity;
import springcloudms.inventoryservice.model.enums.WarehousesEnum;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {


//    void saveAll(List<BookEntity> bookProducts);

    List<BookEntity> findAllByWarehouse(WarehousesEnum warehouse);

    Boolean existsByArticleNoAndQuantity(String articleNo, Integer quantity);

    Optional<BookEntity> findByArticleNo(String articleNo);

    Boolean deleteByArticleNo(String articleNo);
}
