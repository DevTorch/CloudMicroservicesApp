package springcloudms.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.inventoryservice.domain.ProductId;
import springcloudms.inventoryservice.exception.KafkaSenderException;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
import springcloudms.inventoryservice.model.mapper.BookMapper;
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
    @Tag(name = "Book API", description = "Api для работы с книгами.")
    @Operation(summary = "Добавление книги в каталог")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Книга добавлена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "API Request Filed",
                    content = @Content)
    })
    public ResponseEntity<?> addNewBook(@RequestBody BookResponseDTO bookResponseDTO) {

        try {
            bookService.saveBook(bookResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new KafkaSenderException(LocalDateTime.now(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDTO);
    }

    @DeleteMapping(value = "/{articleNo}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Book API", description = "API для работы с книгой.")
    @Operation(summary = "Удаление книги из каталога артикулу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Книга удалена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Книга с таким артикулом не найдена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "API Request Filed",
                    content = @Content)
    })
    public ResponseEntity<HttpStatus> deleteBookByArticleNo(@PathVariable String articleNo) {
        if (Boolean.TRUE.equals(bookService.deleteByArticleNo(articleNo))) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Book API", description = "Api для работы с книгами.")
    @Operation(summary = "Получение книги по ProductId, идентификатору записи в БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Идентификатор найден, данные по книге получены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Книга с таким ProductId не найдена",
                    content = @Content)
    })
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long productId) {

        var response = bookService.findById(productId);

        if (bookService.findById(productId).isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{articleNo}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Book API", description = "Api для работы с книгами.")
    @Operation(summary = "Получение BookResponseDTO по артикулу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Артикул найден, данные по книге получены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Книга с таким артикулом не найдена",
                    content = @Content)
    })
    public ResponseEntity<BookResponseDTO> getBookResponseDTOByArticleNo(@PathVariable ProductId articleNo) {
        if (bookService.findByArticleNo(articleNo).isPresent()) {
            return ResponseEntity.ok(bookService.findByArticleNo(articleNo).get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{articleNo}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookResponseDTO bookResponseDTO, @PathVariable ProductId articleNo) {

        if (bookService.findByArticleNo(articleNo).isPresent()) {
            return ResponseEntity.ok(bookService.update(bookResponseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    @Tag(name = "Book API", description = "Api для работы с книгами.")
    @Operation(summary = "Получение списка всех книг")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вывод всех книг списком.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class))})
    })
    public List<BookResponseDTO> getAllBooks() {
        return bookService.findAll();
    }
}