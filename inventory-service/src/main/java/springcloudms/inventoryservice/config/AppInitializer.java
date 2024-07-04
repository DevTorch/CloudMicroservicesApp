package springcloudms.inventoryservice.config;

import cloudmicroservicesapp.core.enums.ProductTypeEnum;
import cloudmicroservicesapp.core.enums.WarehousesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import springcloudms.inventoryservice.mapper.BookMapper;
import springcloudms.inventoryservice.model.dto.BookCreationDTO;
import springcloudms.inventoryservice.service.BookService;
import springcloudms.inventoryservice.service.InventoryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("!test")
public class AppInitializer implements ApplicationListener<ContextRefreshedEvent>  {

    private final InventoryService inventoryService;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        BookCreationDTO bookDtoOne = new BookCreationDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "Lord of the Rings",
                "J.R.R. TolkienJ",
                "Houghton Mifflin Harcourt",
                "978-1-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("10.00")
        );

        BookCreationDTO bookDtoTwo = new BookCreationDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Hobbit",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-2-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("10.00")
        );

        BookCreationDTO bookDtoThree = new BookCreationDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Fellowship of the Ring",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-3-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("100.00")
        );

        BookCreationDTO bookDtoFour = new BookCreationDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Two Towers",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-4-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("100.00")
        );

        BookCreationDTO bookDtoFive = new BookCreationDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Return of the King",
                "J.R.R. Tolkien",
                "Houghton Mifflin Harcourt",
                "978-5-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("90.00")
        );

        BookCreationDTO bookDtoSix = new BookCreationDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "The Catcher in the Rye",
                "J.D. Salinger",
                "Houghton Mifflin Harcourt",
                "978-6-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("50.00")
        );

        List<BookCreationDTO> books = List.of(bookDtoOne, bookDtoTwo, bookDtoThree, bookDtoFour, bookDtoFive, bookDtoSix);

//        books.forEach(System.out::println);

        System.out.println();

        bookMapper.toEntities(books).forEach(System.out::println);

//        bookService.saveAll(bookMapper.toEntities(books));



    }
}
