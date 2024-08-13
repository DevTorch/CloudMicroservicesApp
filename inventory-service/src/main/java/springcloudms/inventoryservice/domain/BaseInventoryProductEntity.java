package springcloudms.inventoryservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@MappedSuperclass
@Entity
@Table(name = "inventory")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseInventoryProductEntity {

    @EmbeddedId
    @Column(name = "article_number", unique = true, nullable = false, updatable = false)
    private ProductId articleNo;

    @NotNull
    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductTypeEnum productType;

    @Column(name = "product_title")
    @NotNull
    private String title;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Version
    @UpdateTimestamp
    private LocalDateTime lastStockUpdate;

    @NotNull
    @Column(name = "purchase_price", columnDefinition = "DECIMAL(6,2)")
    private BigDecimal purchasePrice;

    public BaseInventoryProductEntity(
            ProductTypeEnum productType,
            String title,
            Integer quantity,
            BigDecimal purchasePrice) {
        this.articleNo = new ProductId();
        this.productType = productType;
        this.title = title;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }
}

