package springcloudms.notificationservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "processed_events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProcessEventEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private String messageId;
    @Column(nullable = false, unique = true)
    private String articleNo;
    @Column
    private String eventType;
    @Column
    private String eventBody;

    public ProcessEventEntity(String articleNo, String eventType, String eventBody) {
        this.articleNo = articleNo;
        this.eventType = eventType;
        this.eventBody = eventBody;
    }
}
