package springcloudms.customerservice.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.customerservice.events.entity.CustomerProcessedEvents;

@Repository
public interface KafkaMessageStorageRepository extends JpaRepository<CustomerProcessedEvents, Long> {

    CustomerProcessedEvents findByMessageId(String messageId);
}
