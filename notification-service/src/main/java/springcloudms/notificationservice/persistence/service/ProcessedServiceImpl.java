package springcloudms.notificationservice.persistence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.notificationservice.persistence.entity.ProcessEventEntity;
import springcloudms.notificationservice.persistence.repository.ProcessedEventRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessedServiceImpl implements ProcessedEventsService {

    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    @Override
    public Optional<ProcessEventEntity> findByMessageId(String messageId) {
        return processedEventRepository.findByMessageId(messageId);
    }


    @Override
    public void save(ProcessEventEntity processEventEntity) {

    }
}
