package springcloudms.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.inventoryservice.exception.KafkaSenderException;
import springcloudms.inventoryservice.mapper.BookMapper;
import springcloudms.inventoryservice.model.BookProduct;
import springcloudms.inventoryservice.model.dto.BookCreationDTO;
import springcloudms.inventoryservice.service.BookService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper mapper;


    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewBook(@RequestBody BookCreationDTO bookCreationDTO) {

        try {
            bookService.saveBook(bookCreationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new KafkaSenderException(LocalDateTime.now(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookCreationDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BookCreationDTO> getBookById(@PathVariable Long productId) {
        var book = bookService.findById(productId);

        return book.map(bookProduct -> ResponseEntity.ok(mapper.toDto(bookProduct))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @GetMapping
    public List<BookProduct> getAllBooks() {
        return bookService.findAll();
    }
}