package springcloudms.notificationservice.persistence.service;

import springcloudms.notificationservice.persistence.entity.ProcessEventEntity;

import java.util.Optional;

public interface ProcessedEventsService {

    Optional<ProcessEventEntity> findByMessageId(String messageId);
    void save(ProcessEventEntity processEventEntity);
}
