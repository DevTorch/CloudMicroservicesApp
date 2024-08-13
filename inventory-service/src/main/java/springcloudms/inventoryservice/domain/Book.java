package springcloudms.inventoryservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_books")
@ToString
@Builder
public class Book extends BaseInventoryProductEntity {
    @NotNull
    private String author;
    @NotNull
    private String publisher;
    @Column(name = "ISBN", unique = true)
    @NotNull
    private ISBN isbn;

}
