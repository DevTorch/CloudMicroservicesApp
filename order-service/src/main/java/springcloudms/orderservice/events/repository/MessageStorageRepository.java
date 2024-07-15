package springcloudms.orderservice.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.orderservice.events.entity.AccountOrderProcessedEvent;

import java.util.Optional;

@Repository
public interface MessageStorageRepository extends JpaRepository<AccountOrderProcessedEvent, Long> {

    Optional<AccountOrderProcessedEvent> findByMessageId(String messageId);
}
