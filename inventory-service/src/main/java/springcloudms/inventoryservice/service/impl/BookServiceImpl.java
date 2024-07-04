package springcloudms.inventoryservice.service.impl;

import cloudmicroservicesapp.core.AddNewBookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.inventoryservice.exception.KafkaSenderException;
import springcloudms.inventoryservice.mapper.BookMapper;
import springcloudms.inventoryservice.model.BookProduct;
import springcloudms.inventoryservice.model.dto.BookCreationDTO;
import springcloudms.inventoryservice.repository.BookRepository;
import springcloudms.inventoryservice.service.BookService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final KafkaTemplate<String, AddNewBookEvent> kafkaTemplate;

    @Override
    public Optional<BookProduct> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public BookProduct save(BookProduct entity) {
        return null;
    }

    @Override
    public BookProduct update(BookProduct entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    @Transactional
    public void saveBook(BookCreationDTO bookCreationDTO) throws ExecutionException, InterruptedException {

        final BookProduct bookProduct = BookMapper.mapDtoToEntity(bookCreationDTO);

        bookRepository.save(bookProduct);
        log.info("Book saved successfully {}: ", bookProduct);

        AddNewBookEvent event = new AddNewBookEvent(
                bookCreationDTO.articleNo(),
                bookCreationDTO.productType(),
                bookCreationDTO.title(),
                bookCreationDTO.author(),
                bookCreationDTO.publisher(),
                bookCreationDTO.isbnNo(),
                bookCreationDTO.warehouse(),
                bookCreationDTO.quantity(),
                bookCreationDTO.purchasePrice()
        );

        /* SYNC CALL
        SendResult<String, AddNewBookEvent> result = kafkaTemplate
                .send("product-created-events-topic", event.articleNo(), event).get();*/

//      ASYNC CALLBACK
        final CompletableFuture<SendResult<String, AddNewBookEvent>> send =
                kafkaTemplate.send("product-created-events-topic", event.articleNo(), event);

        send.whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        log.info("Book event sent successfully {}: ", result.getRecordMetadata());

                        log.info("Topic: {}", result.getRecordMetadata().topic());
                        log.info("Partition: {}", result.getRecordMetadata().partition());
                        log.info("Offset: {}", result.getRecordMetadata().offset());
                    } else {
                        log.error("Book event failed to send: ", ex);
                        throw new KafkaSenderException(LocalDateTime.now(), ex.getMessage());
                    }
                });
    }

    @Override
    public void saveAll(List<BookProduct> bookProducts) {
        bookRepository.saveAll(bookProducts);
    }

    @Override
    public List<BookProduct> findAll() {
        return bookRepository.findAll();
    }

}
