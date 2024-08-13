package springcloudms.customerservice.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.customerservice.events.entity.CustomerProcessedEvents;
import springcloudms.customerservice.events.repository.KafkaMessageStorageRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KafkaMessageStorageServiceImpl implements KafkaMessageStorageService {

    private final KafkaMessageStorageRepository messageRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerProcessedEvents> findByMessageId(String messageId) {
        return Optional.ofNullable(messageRepository.findByMessageId(messageId));
    }

    @Override
    @Transactional
    public void saveMessage(CustomerProcessedEvents event) {
        messageRepository.save(event);
    }
}
