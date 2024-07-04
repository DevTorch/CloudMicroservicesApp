package springcloudms.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import springcloudms.inventoryservice.model.BookProduct;
import springcloudms.inventoryservice.model.dto.BookCreationDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper implements Mappable<BookProduct, BookCreationDTO> {

    public BookProduct toEntity(BookCreationDTO dto) {

        return mapDtoToEntity(dto);
    }

    public static BookProduct mapDtoToEntity(BookCreationDTO dto) {
        BookProduct book = new BookProduct();
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

    public List<BookProduct> toEntities(List<BookCreationDTO> dtos) {

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public BookCreationDTO toDto(BookProduct entity) {
        return new BookCreationDTO(
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

    public List<BookCreationDTO> toDto(List<BookProduct> entities) {
        return List.of();
    }
}
