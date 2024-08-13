package springcloudms.inventoryservice.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;
import springcloudms.inventoryservice.model.mapper.BookMapper;
import springcloudms.inventoryservice.service.BookService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Profile("!test")
public class BookDataBaseInitializer {

    private final List<BookResponseDTO> books;
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookDataBaseInitializer(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;

        BookResponseDTO bookDtoOne = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "Lord of the Rings",
                "J.R.R. TolkienJ",
                "Houghton Mifflin Harcourt",
                "978-1-56619-909-9",
                55,
                new BigDecimal("10.00")
        );

        BookResponseDTO bookDtoTwo = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Hobbit",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-2-56619-909-9",
                55,
                new BigDecimal("10.00")
        );

        BookResponseDTO bookDtoThree = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Fellowship of the Ring",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-3-56619-909-9",
                55,
                new BigDecimal("100.00")
        );

        BookResponseDTO bookDtoFour = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Two Towers",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-4-56619-909-9",
                55,
                new BigDecimal("100.00")
        );

        BookResponseDTO bookDtoFive = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Return of the King",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-5-56619-909-9",
                55,
                new BigDecimal("90.00")
        );

        BookResponseDTO bookDtoSix = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Catcher in the Rye",
                "J.D. Salinger",
                "Houghton Mifflin Harcourt",
                "978-6-56619-909-9",
                55,
                new BigDecimal("50.00")
        );

        books = List.of(bookDtoOne, bookDtoTwo, bookDtoThree, bookDtoFour, bookDtoFive, bookDtoSix);

        books.forEach(System.out::println);
    }

    @PostConstruct
    public void bookInit() {
        log.info("Initializing books...");
//        bookService.saveAll(bookMapper.toEntities(books));
//        log.info("Books initialized %s", Arrays.toString(books.toArray()));
    }

}
