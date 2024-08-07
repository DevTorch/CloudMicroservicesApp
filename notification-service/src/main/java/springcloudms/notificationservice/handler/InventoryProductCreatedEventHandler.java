package springcloudms.notificationservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import springcloudms.notificationservice.events.AddNewBookEvent;
import springcloudms.notificationservice.events.AddNewElectronicsEvent;
import springcloudms.notificationservice.exception.NonRetryableException;
import springcloudms.notificationservice.exception.RetryableException;
import springcloudms.notificationservice.persistence.repository.ProcessedEventRepository;
import springcloudms.notificationservice.persistence.entity.ProcessEventEntity;
import springcloudms.notificationservice.persistence.service.ProcessedEventsService;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {"product-created-events-topic"}, groupId = "cloudmicroservices-consumer-group")
public class InventoryProductCreatedEventHandler {

    private final RestTemplate restTemplate;
    private final ProcessedEventRepository processedEventRepository;
    private final ProcessedEventsService processedEventsService;

//    @Transactional
//    @RetryableTopic(kafkaTemplate = "retryableTopicKafkaTemplate")
    @KafkaHandler
    public void handleNewBookEvent(
            @Payload AddNewBookEvent event,
            @Header("messageId") String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        log.info("New Book created event received: {}", event.toString());

        var getMessageId = processedEventsService.findByMessageId(messageId);
//        var getMessageId = processedEventRepository.findByMessageId(messageId);

        if (getMessageId.isPresent()) {
            log.info("Message already processed: {}", getMessageId.get());
            return;
        }

        try {
            String uri = "http://localhost:9090/api/response/200";
            var response = restTemplate
                    .exchange(uri, HttpMethod.GET, null, String.class);

            if (response.getStatusCode().value() == HttpStatus.OK.value()) {
                log.info("Response from mock service: {}", response.getBody());
                log.info("New Book created event received TRY/CATCH: {}", event.toString());
            }

        } catch (ResourceAccessException e) {
            log.error("Failed to call mock service", e);
            throw new RetryableException("Failed to call mock service", e.getCause());
        } catch (HttpServerErrorException e) {
            log.error("Failed to call mock service", e);
            throw new NonRetryableException("Failed to call mock service", e.getCause());
        } catch (Exception ex) {
            log.error("Failed to call mock service", ex);
            throw new NonRetryableException("Failed to call mock service", ex.getCause());
        }

        try {
            processedEventsService.save(new ProcessEventEntity(
                    messageId,
                    messageKey,
                    "Book",
                    event.toString()));
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to save processed event", e);
            throw new NonRetryableException("Failed to save processed event", e.getCause());
        }
    }

    @DltHandler
    public void handleDltNewBookEvent(AddNewBookEvent event, String topic) {
        log.info("Event on DLT topic {}, payload={}", topic, event.toString());
    }

    @KafkaHandler
    public void handleNewElectronics(AddNewElectronicsEvent event) {
        log.info("New Electronics created event received: {}", event.title());
    }
}
