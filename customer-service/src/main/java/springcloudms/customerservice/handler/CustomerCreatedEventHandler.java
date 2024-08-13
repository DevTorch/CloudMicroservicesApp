package springcloudms.customerservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import springcloudms.core.constants.CoreConstants;
import springcloudms.customerservice.dto.CustomerRequestSignUpDTO;
import springcloudms.customerservice.events.CustomerCreateRequestEvent;
import springcloudms.customerservice.events.entity.CustomerProcessedEvents;
import springcloudms.customerservice.events.service.KafkaMessageStorageService;
import springcloudms.customerservice.service.CustomerService;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {CoreConstants.CUSTOMER_SIGNUP_EVENTS_TOPIC}, groupId = CoreConstants.GROUP_ID)
public class CustomerCreatedEventHandler {

    private final KafkaMessageStorageService messageStorageService;
    private final CustomerService customerService;

    @KafkaHandler
    public void handleCustomerCreatedEvent(
            @Payload CustomerCreateRequestEvent event,
            @Header("messageId") String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey,
            @Header("eventType") String eventType) {

        log.info("Received customer created event: {}", event);

        var customerEventByMessageId = messageStorageService.findByMessageId(messageId);

        if (customerEventByMessageId.isPresent()) {
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

        customerService.createNewCustomer(
                CustomerRequestSignUpDTO.builder()
                .accountId(event.accountId())
                .fullName(event.fullName())
                .nickname(event.nickname())
                .build());
        log.info("Customer created successfully: {}", event);

    }

    @DltHandler
    void handleDlt(CustomerCreateRequestEvent event, String topic) {
        log.info("Event on DLT topic {}, payload={}", topic, event.toString());

    }
}
