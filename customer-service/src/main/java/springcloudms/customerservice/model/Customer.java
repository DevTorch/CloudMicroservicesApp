package springcloudms.customerservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "customer")

public class Customer extends BaseCustomerEntity {

    @Builder
    public Customer() {
        super();
    }

}
