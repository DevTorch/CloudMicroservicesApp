package springcloudms.inventoryservice;

import cloudmicroservicesapp.core.enums.ProductTypeEnum;
import cloudmicroservicesapp.core.enums.WarehousesEnum;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.inventoryservice.model.BookEntity;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
import springcloudms.inventoryservice.repository.BookRepository;
import springcloudms.inventoryservice.service.BookService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class DataBaseMethodTests {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImplApplicationTests.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    void shouldAddNewBook() {

        BookResponseDTO bookResponseDTO = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "Harry Potter and the Philosopher's Stone",
                "J.K. Rowling",
                "Houghton Mifflin Harcourt",
                "978-1-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("10.00")
        );

        BookEntity book_one = new BookEntity();
        book_one.setArticleNo(UUID.randomUUID().toString());
        book_one.setWarehouse(WarehousesEnum.WAREHOUSE_SEATTLE);
        book_one.setQuantity(55);
        book_one.setProductType(ProductTypeEnum.BOOKS);
        book_one.setPurchasePrice(new BigDecimal("10.00"));
        book_one.setLastStockUpdate(LocalDateTime.now());
        book_one.setIsbnNo("978-1-56619-909-9");
        book_one.setAuthor("J.K. Rowling");
        book_one.setPublisher("Houghton Mifflin Harcourt");
        book_one.setTitle("Harry Potter and the Philosopher's Stone");

        entityManager.persist(book_one);
        entityManager.flush();

        assertThat(book_one.getId(), Matchers.greaterThan(0L));
        log.info("Book id: " + book_one);
    }
}
