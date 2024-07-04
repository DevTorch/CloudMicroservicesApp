package springcloudms.inventoryservice.model.base;

import cloudmicroservicesapp.core.enums.ProductTypeEnum;
import cloudmicroservicesapp.core.enums.WarehousesEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
//@MappedSuperclass
@Entity
@Table(name = "inventory")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseInventoryProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_id_generator")
    @SequenceGenerator(name = "base_id_generator",
            sequenceName = "products_id_generator",
            allocationSize = 1,
            initialValue = 1001)
    private Long id;

    @NotNull
    @Column(name = "article_number", unique = true)
    private String articleNo;

    @Column(name = "warehouse")
    @Enumerated(EnumType.STRING)
    private WarehousesEnum warehouse;

    @NotNull
    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductTypeEnum productType;

    @Column(name = "product_title")
    private String title;

    @Column(name = "quantity")
    private Integer quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime lastStockUpdate;

    @NotNull
    @Column(name = "purchase_price", columnDefinition = "DECIMAL(6,2)")
    private BigDecimal purchasePrice;

    public BaseInventoryProductEntity() {
        this.articleNo = UUID.randomUUID().toString();
    }
}

