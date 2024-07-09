package springcloudms.inventoryservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springcloudms.inventoryservice.exception.BookNotFoundException;
import springcloudms.inventoryservice.exception.KafkaSenderException;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(KafkaSenderException.class)
    public ResponseEntity<ErrorCodes> handleKafkaBackoffException(KafkaSenderException e) {
        return ResponseEntity.status(500).body(ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCodes> handleException(Exception e) {

//        log.error("Exception: {} ", e.toString());
//
//        return ResponseEntity
//                .status(500)
//                .body(new ErrorResponse(500, e.getMessage()));

        return null;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorCodes> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(404).body(ErrorCodes.NOT_FOUND.getErrorCode());
    }
}
