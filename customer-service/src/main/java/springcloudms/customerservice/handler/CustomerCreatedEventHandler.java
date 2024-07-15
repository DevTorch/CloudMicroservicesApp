package springcloudms.customerservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import springcloudms.customerservice.events.CustomerCreateRequestEvent;
import springcloudms.customerservice.events.entity.CustomerProcessedEvents;
import springcloudms.customerservice.events.service.KafkaMessageStorageService;
import springcloudms.customerservice.exception.NonRetryableException;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {"customer-signup-event-topic"}, groupId = "cloudmicroservices-consumer-group")
public class CustomerCreatedEventHandler {

    private final KafkaMessageStorageService messageStorageService;

    @KafkaListener
    public void handleCustomerCreatedEvent(
            @Payload CustomerCreateRequestEvent event,
            @Header("messageId") String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey,
            @Header("eventType") String eventType) {

        log.info("Received customer created event: {}", event);

        var customerEventByMessageId = messageStorageService.findByMessageId(messageId);

        if (customerEventByMessageId.isEmpty()) {
            log.warn("Message already processed: {}", messageId);
            return;
        }

        messageStorageService.saveMessage(new CustomerProcessedEvents(
                messageId,
                Long.valueOf(messageKey),
                eventType,
                event.toString(),
                event.persistDateTime()
        ));
    }
}
