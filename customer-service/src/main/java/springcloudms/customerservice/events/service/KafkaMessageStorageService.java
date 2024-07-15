package springcloudms.customerservice.events.service;

import springcloudms.customerservice.events.entity.CustomerProcessedEvents;

import java.util.Optional;

public interface KafkaMessageStorageService {

    Optional<CustomerProcessedEvents> findByMessageId(String messageId);

    void saveMessage(CustomerProcessedEvents event);
}
