package springcloudms.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springcloudms.inventoryservice.model.BookProduct;

import java.util.List;

public interface BookRepository extends JpaRepository<BookProduct, Long> {


//    void saveAll(List<BookProduct> bookProducts);
    List<BookProduct> findAllByWarehouse(WarehousesEnum warehouse);

    Boolean existsByIdAndQuantity(Long id, Integer quantity);
}
