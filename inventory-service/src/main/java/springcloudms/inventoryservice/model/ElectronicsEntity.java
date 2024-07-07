package springcloudms.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springcloudms.inventoryservice.model.base.BaseInventoryProductEntity;
import springcloudms.inventoryservice.model.enums.ElectronicCategoryEnum;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_electronics")
@Builder
public class ElectronicsEntity extends BaseInventoryProductEntity {

    @Enumerated(EnumType.STRING)
    private ElectronicCategoryEnum category;
    private String model;
    private String characteristics;
    private String description;

}
