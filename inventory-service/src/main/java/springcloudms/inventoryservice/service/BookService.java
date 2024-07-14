package springcloudms.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import springcloudms.inventoryservice.model.BookEntity;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface BookService extends AbstractService<BookResponseDTO, Long> {
    void saveBook(BookResponseDTO bookProductDTO) throws ExecutionException, InterruptedException, JsonProcessingException;

    void saveAll(List<BookEntity> bookEntities);

    List<BookResponseDTO> findAll();

    Optional<BookResponseDTO> findByArticleNo(String articleNo);

    Boolean deleteByArticleNo(String articleNo);

    Optional<String> getArticleNoByISBN(String isbnNo);
}
