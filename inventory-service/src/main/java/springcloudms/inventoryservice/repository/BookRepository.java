package springcloudms.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.inventoryservice.domain.Book;
import springcloudms.inventoryservice.domain.ISBN;
import springcloudms.inventoryservice.domain.ProductId;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    Boolean existsByArticleNoAndQuantity(ProductId articleNo, Integer quantity);

    Optional<Book> findByIsbn(ISBN isbnNo);
}
