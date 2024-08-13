package springcloudms.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springcloudms.orderservice.model.AccountOrder;

@Repository
public interface AccOrderRepository extends JpaRepository<AccountOrder, Long> {
}
