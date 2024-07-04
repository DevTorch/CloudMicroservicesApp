package springcloudms.inventoryservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class KafkaSenderException extends RuntimeException {

    private LocalDateTime timestamp;
    private String message;

}
