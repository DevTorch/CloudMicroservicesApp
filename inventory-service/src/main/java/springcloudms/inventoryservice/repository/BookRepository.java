package springcloudms.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.inventoryservice.model.BookEntity;
import springcloudms.inventoryservice.model.enums.WarehousesEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {


//    void saveAll(List<BookEntity> bookProducts);

    List<BookEntity> findAllByWarehouse(WarehousesEnum warehouse);

    Boolean existsByArticleNoAndQuantity(String articleNo, Integer quantity);

    Optional<BookEntity> findByArticleNo(String articleNo);

    Boolean deleteByArticleNo(String articleNo);

    Optional<BookEntity> findByIsbnNo(String isbnNo);
}
