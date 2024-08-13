package springcloudms.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "complete_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderComplete {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_generator")
    @SequenceGenerator(name = "orders_id_generator",
            sequenceName = "orders_id_sequence",
            allocationSize = 1,
            initialValue = 101)
    private String orderNumber;
    private BigDecimal totalCost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime orderDate;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<OrderItems> products;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "orderNumber = " + orderNumber + ", " +
               "orderDate = " + orderDate + ", " +
               "totalCost = " + totalCost + ")";
    }
}
