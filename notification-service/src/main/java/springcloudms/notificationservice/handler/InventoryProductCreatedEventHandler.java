package springcloudms.notificationservice.handler;

import cloudmicroservicesapp.core.AddNewBookEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "product-created-events-topic")
public class InventoryProductCreatedEventHandler {

    @KafkaHandler
    public void handle(AddNewBookEvent event) {

        log.info("Product created event received: {}", event.title());

    }
}
