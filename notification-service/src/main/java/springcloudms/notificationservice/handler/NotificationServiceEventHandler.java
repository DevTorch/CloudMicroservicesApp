package springcloudms.notificationservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springcloudms.core.constants.CoreConstants;
import springcloudms.notificationservice.events.AddNewBookEvent;
import springcloudms.notificationservice.events.AddNewElectronicsEvent;
import springcloudms.notificationservice.persistence.entity.ProcessEventEntity;
import springcloudms.notificationservice.persistence.service.ProcessedEventsService;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {
        CoreConstants.PRODUCT_CREATED_EVENTS_TOPIC,
        CoreConstants.CUSTOMER_ACTIVITY_EVENTS_TOPIC},
        groupId = CoreConstants.GROUP_ID)
//@KafkaListener(topics = {"product-created-events-topic"}, groupId = "cloudmicroservices-consumer-group")
public class NotificationServiceEventHandler {

    private final RestTemplate restTemplate;
    private final ProcessedEventsService processedEventsService;

    @KafkaHandler
    public void handleNewBookEvent(
            @Payload AddNewBookEvent event,
            @Header("messageId") String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        log.info("New Book created events received: {}", event.toString());

        var getMessageId = processedEventsService.findByMessageId(messageId);
//        var getMessageId = processedEventRepository.findByMessageId(messageId);

        if (getMessageId.isPresent()) {
            log.info("Message already processed: {}", getMessageId.get());
            return;
        }

//        try {
//            String uri = "http://localhost:9090/api/response/200";
//            var response = restTemplate
//                    .exchange(uri, HttpMethod.GET, null, String.class);
//
//            if (response.getStatusCode().value() == HttpStatus.OK.value()) {
//                log.info("Response from mock service: {}", response.getBody());
//                log.info("New Book created events received TRY/CATCH: {}", event);
//            }
//
//        } catch (ResourceAccessException e) {
//            log.error("Failed to call mock service", e);
//            throw new RetryableException("Failed to call mock service", e.getCause());
//        } catch (HttpServerErrorException e) {
//            log.error("Failed to call mock service", e);
//            throw new NonRetryableException("Failed to call mock service", e.getCause());
//        } catch (Exception ex) {
//            log.error("Failed to call mock service", ex);
//            throw new NonRetryableException("Failed to call mock service", ex.getCause());
//        }

        processedEventsService.save(new ProcessEventEntity(
                messageId,
                messageKey,
                "Book",
                event.toString()));
    }

    @DltHandler
    public void handleDltNewBookEvent(AddNewBookEvent event, String topic) {
        log.info("Event on DLT topic {}, payload={}", topic, event.toString());
    }

    @KafkaHandler
    public void handleNewElectronics(AddNewElectronicsEvent event) {
        log.info("New Electronics created events received: {}", event.title());
    }

    //TODO Регистрация, логин, такое
    public void handleCustomerActivityEvents(){
        log.info("Customer activity events received: ");
    }
}
