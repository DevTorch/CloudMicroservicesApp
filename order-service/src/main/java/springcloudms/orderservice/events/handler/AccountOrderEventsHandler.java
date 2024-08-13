package springcloudms.orderservice.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import springcloudms.core.constants.CoreConstants;
import springcloudms.orderservice.events.OrderAccountCreateRequestEvent;
import springcloudms.orderservice.events.entity.AccountOrderProcessedEvent;
import springcloudms.orderservice.events.service.MessageStorageService;
import springcloudms.orderservice.model.AccountOrder;
import springcloudms.orderservice.service.OrderService;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = CoreConstants.ACCOUNT_ORDER_EVENTS_TOPIC, groupId = CoreConstants.GROUP_ID)
public class AccountOrderEventsHandler {

    private final MessageStorageService messageService;
    private final OrderService orderService;

    @KafkaHandler
    public void handleAccountOrderCreateEvent(
            @Payload OrderAccountCreateRequestEvent event,
            @Header("messageId") String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey,
            @Header("eventType") String eventType) {

        log.info("Order Account created events received: {}", event.toString());

        if (messageService.isMessageProcessed(messageId)) {
            log.info("Message already processed: {}", messageId);
            return;
        }

        messageService.saveMessage(new AccountOrderProcessedEvent(
                messageId,
                Long.valueOf(messageKey),
                event.toString(),
                eventType,
                event.createdTimeStamp()));

        var accountOrder = AccountOrder.builder()
                .accountId(event.accountId())
                .createdTimeStamp(event.createdTimeStamp() == null
                        ? LocalDateTime.now()
                        : event.createdTimeStamp())
                .build();

        orderService.createAccountOrder(accountOrder);
    }

}
