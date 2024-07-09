package springcloudms.notificationservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import springcloudms.notificationservice.events.AddNewBookEvent;
import springcloudms.notificationservice.events.AddNewElectronicsEvent;
import springcloudms.notificationservice.exception.NonRetryableException;
import springcloudms.notificationservice.exception.RetryableException;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "product-created-events-topic")
public class InventoryProductCreatedEventHandler {

    private final RestTemplate restTemplate;

    @KafkaHandler
    public void handleNewBook(AddNewBookEvent event) {
        log.info("New Book created event received: {}", event.toString());
    }

    @KafkaHandler
    public void handleNewElectronics(AddNewElectronicsEvent event) {
        log.info("New Electronics created event received: {}", event.title());
    }
}
