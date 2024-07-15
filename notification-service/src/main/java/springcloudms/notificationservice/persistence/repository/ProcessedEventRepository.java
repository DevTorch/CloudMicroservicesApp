package springcloudms.notificationservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.notificationservice.persistence.entity.ProcessEventEntity;

import java.util.Optional;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessEventEntity, String> {

    Optional<ProcessEventEntity> findByMessageId(String messageId);

    Optional<ProcessEventEntity> findByArticleNo(String articleNo);
}
