package springcloudms.orderservice.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.orderservice.events.entity.AccountOrderProcessedEvent;
import springcloudms.orderservice.events.repository.MessageStorageRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageStorageServiceImpl implements MessageStorageService {

    private final MessageStorageRepository messageRepository;

    @Override
    @Transactional
    public void saveMessage(AccountOrderProcessedEvent accountOrderProcessedEvent) {
        messageRepository.save(accountOrderProcessedEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isMessageProcessed(String messageId) {
        return messageRepository.findByMessageId(messageId).isPresent();
    }
}
