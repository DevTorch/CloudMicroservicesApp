package springcloudms.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import springcloudms.inventoryservice.domain.Book;
import springcloudms.inventoryservice.domain.ProductId;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface BookService {

    void saveBook(BookResponseDTO bookProductDTO) throws ExecutionException, InterruptedException, JsonProcessingException;

    void saveAll(List<Book> bookEntities);

    List<BookResponseDTO> findAll();

    Optional<BookResponseDTO> findByArticleNo(ProductId articleNo);

    Boolean deleteByArticleNo(String articleNo);

    Optional<String> getArticleNoByISBN(String isbnNo);
}
