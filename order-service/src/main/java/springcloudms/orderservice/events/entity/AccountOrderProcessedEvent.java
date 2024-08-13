package springcloudms.orderservice.events.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_processed_events")
@NoArgsConstructor
@Getter
@Setter
public class AccountOrderProcessedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String messageId;

    @Column(nullable = false, unique = true)
    private Long accountId;

    private String eventBody;
    private String eventType;
    private LocalDateTime receivedTimeStamp;

    public AccountOrderProcessedEvent(String messageId,
                                      Long accountId,
                                      String eventBody,
                                      String eventType,
                                      LocalDateTime receivedTimeStamp) {
        this.messageId = messageId;
        this.accountId = accountId;
        this.eventBody = eventBody;
        this.eventType = eventType;
        this.receivedTimeStamp = receivedTimeStamp;
    }
}
