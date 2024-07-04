package springcloudms.customerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "customers")
public class BaseCustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
    @SequenceGenerator(name = "customer_id_generator",
            sequenceName = "customer_id_seq",
            allocationSize = 20,
            initialValue = 101)
    private Long id;

    @NotNull
    @Column(name = "account_id", unique = true)
    private Long accountId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "registration_date", updatable = false)
    @CreationTimestamp
    protected LocalDateTime persistDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseCustomerEntity customer = (BaseCustomerEntity) o;
        return Objects.equals(id, customer.id) && Objects.equals(nickname, customer.nickname) && Objects.equals(fullName, customer.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, fullName);
    }
}
