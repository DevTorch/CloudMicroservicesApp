package springcloudms.orderservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {"customer-shopping-cart-event-topic"}, groupId = "cloudmicroservices-consumer-group")
public class OrderEventsHandler {


}
