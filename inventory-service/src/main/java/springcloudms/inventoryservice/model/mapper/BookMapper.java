package springcloudms.inventoryservice.model.mapper;

import org.springframework.stereotype.Component;
import springcloudms.inventoryservice.domain.Book;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper implements Mappable<Book, BookResponseDTO> {

    public Book toEntity(BookResponseDTO dto) {

        return mapDtoToEntity(dto);
    }

    public static Book mapDtoToEntity(BookResponseDTO dto) {

        Book book = new Book();
        book.setArticleNo(dto.articleNo());
        book.setQuantity(dto.quantity());
        book.setProductType(dto.productType());
        book.setPurchasePrice(dto.purchasePrice());
        book.setLastStockUpdate(LocalDateTime.now());
        book.setIsbnNo(dto.isbnNo());
        book.setAuthor(dto.author());
        book.setPublisher(dto.publisher());
        book.setTitle(dto.title());
        return book;
    }

    public List<Book> toEntities(List<BookResponseDTO> dtos) {

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public BookResponseDTO toDto(Book entity) {
        return new BookResponseDTO(
                entity.getArticleNo(),
                entity.getProductType(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublisher(),
                entity.getIsbnNo(),
                entity.getQuantity(),
                entity.getPurchasePrice()
        );
    }

    public List<BookResponseDTO> toDto(List<Book> entities) {
        return List.of();
    }
}
