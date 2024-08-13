package springcloudms.inventoryservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.inventoryservice.domain.Book;
import springcloudms.inventoryservice.domain.ProductId;
import springcloudms.inventoryservice.events.AddNewBookEvent;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
import springcloudms.inventoryservice.model.mapper.BookMapper;
import springcloudms.inventoryservice.repository.BookRepository;
import springcloudms.inventoryservice.service.BookService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BookMapper bookMapper;

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    @Transactional
    public void saveBook(BookResponseDTO bookResponseDTO) throws ExecutionException, InterruptedException, JsonProcessingException {

        final Book book = BookMapper.mapDtoToEntity(bookResponseDTO);

        bookRepository.save(book);

        log.info("Book saved successfully {}: ", new ObjectMapper().writeValueAsString(book));

        AddNewBookEvent event = new AddNewBookEvent(
                book.getArticleNo(),
                bookResponseDTO.productType(),
                bookResponseDTO.title(),
                bookResponseDTO.author(),
                bookResponseDTO.publisher(),
                bookResponseDTO.isbnNo(),
        );

        log.info("Event: {}", event);

        //KAFKA MESSAGE ID HEADERS
        String PRODUCT_TOPIC = "product-created-events-topic";

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                PRODUCT_TOPIC,
                event.articleNo(),
                event);

        record.headers().add("messageId", UUID.randomUUID().toString().getBytes())
                .add("timestamp", LocalDateTime.now().toString().getBytes())
                .add("eventType", "BookCreated".getBytes());


        /* SYNC CALL via .get() blocking method */
        SendResult<String, Object> result = kafkaTemplate.send(record).get();

        log.info("Book events sent successfully {}: ", result.getRecordMetadata());

//      ASYNC CALLBACK
//        final CompletableFuture<SendResult<String, Object>> send =
//                kafkaTemplate.send(record);
//
//        send.whenComplete(
//                (result, ex) -> {
//                    if (ex == null) {
//                        log.info("Book events sent successfully {}: ", result.getRecordMetadata());
//
//                        log.info("Topic: {}", result.getRecordMetadata().topic());
//                        log.info("Partition: {}", result.getRecordMetadata().partition());
//                        log.info("Offset: {}", result.getRecordMetadata().offset());
//                    } else {
//                        log.error("Book events failed to send: ", ex);
//                        throw new KafkaSenderException(LocalDateTime.now(), ex.getMessage());
//                    }
//                });
    }

    @Override
    public void saveAll(List<Book> bookEntities) {
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

    @Override
    public Optional<BookResponseDTO> findByArticleNo(ProductId articleNo) {
        return bookRepository.findByIsbn(articleNo).get().getArticleNo();
    }

    @Override
    public Boolean deleteByArticleNo(String articleNo) {
        return null;
    }

    @Override
    public Optional<String> getArticleNoByISBN(String isbnNo) {
        return Optional.empty();
    }

}
