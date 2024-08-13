package springcloudms.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springcloudms.inventoryservice.domain.BaseInventoryProductEntity;
import springcloudms.inventoryservice.model.dto.ProductResponseDTO;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<BaseInventoryProductEntity, Long> {

        @Query(value = """
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM BaseInventoryProductEntity p
                WHERE p.articleNo = :articleNo
                AND p.quantity >= :quantity
        """)
        Boolean isInStock(String articleNo, Integer quantity);


    @Query(value = """
                    SELECT new springcloudms.inventoryservice.model.dto.ProductResponseDTO
                    (p.articleNo, p.title, p.productType, p.quantity)
                    FROM BaseInventoryProductEntity p
                    WHERE p.articleNo = :articleNo
            """)
    Optional<ProductResponseDTO> findProductDTOByArticleNo(String articleNo);

}
