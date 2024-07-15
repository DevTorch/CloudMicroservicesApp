package springcloudms.orderservice.events.service;

import springcloudms.orderservice.events.entity.AccountOrderProcessedEvent;

public interface MessageStorageService {

    void saveMessage(AccountOrderProcessedEvent accountOrderProcessedEvent);
    Boolean isMessageProcessed(String messageId);
}
