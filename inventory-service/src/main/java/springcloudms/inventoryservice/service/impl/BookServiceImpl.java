package springcloudms.inventoryservice.service.impl;

import cloudmicroservicesapp.core.events.AddNewBookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.inventoryservice.exception.KafkaSenderException;
import springcloudms.inventoryservice.mapper.BookMapper;
import springcloudms.inventoryservice.model.BookEntity;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
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
    private final BookMapper bookMapper;

    @Override
    public Optional<BookResponseDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public BookResponseDTO save(BookResponseDTO entity) {
        return null;
    }

    @Override
    public BookResponseDTO update(BookResponseDTO entity) {
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
    public void saveBook(BookResponseDTO bookResponseDTO) throws ExecutionException, InterruptedException {

        final BookEntity bookEntity = BookMapper.mapDtoToEntity(bookResponseDTO);

        bookRepository.save(bookEntity);
        log.info("Book saved successfully {}: ", bookEntity);

        AddNewBookEvent event = new AddNewBookEvent(
                bookResponseDTO.articleNo(),
                bookResponseDTO.productType(),
                bookResponseDTO.title(),
                bookResponseDTO.author(),
                bookResponseDTO.publisher(),
                bookResponseDTO.isbnNo(),
                bookResponseDTO.warehouse(),
                bookResponseDTO.quantity(),
                bookResponseDTO.purchasePrice()
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
    public void saveAll(List<BookEntity> bookEntities) {
        bookRepository.saveAll(bookEntities);
    }

    @Override
    public List<BookResponseDTO> findAll() {
        return bookRepository
                .findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    /**
     * Finds a book by its article number.
     *
     * @param articleNo The article number of the book.
     * @return An optional containing the book, if found, or an empty optional.
     */
    @Override
    public Optional<BookResponseDTO> findByArticleNo(String articleNo) {
        var book = bookRepository.findByArticleNo(articleNo);
        return book.map(bookMapper::toDto);
    }

    @Override
    public Boolean deleteByArticleNo(String articleNo) {
        return bookRepository.deleteByArticleNo(articleNo);
    }

}
