package springcloudms.inventoryservice.model.mapper;

import org.springframework.stereotype.Component;
import springcloudms.inventoryservice.model.BookEntity;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
import springcloudms.inventoryservice.service.BookService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper implements Mappable<BookEntity, BookResponseDTO> {

    public BookEntity toEntity(BookResponseDTO dto) {

        return mapDtoToEntity(dto);
    }

    public static BookEntity mapDtoToEntity(BookResponseDTO dto) {

        BookEntity book = new BookEntity();
        book.setArticleNo(dto.articleNo());
        book.setWarehouse(dto.warehouse());
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

    public List<BookEntity> toEntities(List<BookResponseDTO> dtos) {

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public BookResponseDTO toDto(BookEntity entity) {
        return new BookResponseDTO(
                entity.getArticleNo(),
                entity.getProductType(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublisher(),
                entity.getIsbnNo(),
                entity.getWarehouse(),
                entity.getQuantity(),
                entity.getPurchasePrice()
        );
    }

    public List<BookResponseDTO> toDto(List<BookEntity> entities) {
        return List.of();
    }
}
