package springcloudms.inventoryservice.service;

import springcloudms.inventoryservice.model.BookProduct;
import springcloudms.inventoryservice.model.dto.BookCreationDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BookService extends AbstractService<BookProduct, Long> {
    void saveBook(BookCreationDTO bookProductDTO) throws ExecutionException, InterruptedException;
    void saveAll(List<BookProduct> bookProducts);
    List<BookProduct> findAll();
}
