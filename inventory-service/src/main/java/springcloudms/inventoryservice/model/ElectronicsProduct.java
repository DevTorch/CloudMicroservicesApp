package springcloudms.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springcloudms.inventoryservice.model.base.BaseInventoryProductEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_electronics")
@Builder
public class ElectronicsProduct extends BaseInventoryProductEntity {

    private String type;
    private String model;
    private String characteristics;
    private String description;

}
