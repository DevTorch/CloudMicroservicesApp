package springcloudms.inventoryservice.config;

import lombok.extern.slf4j.Slf4j;
import springcloudms.inventoryservice.model.dto.ElectronicsResponseDTO;
import springcloudms.inventoryservice.model.enums.ElectronicCategoryEnum;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;
import springcloudms.inventoryservice.service.ElectronicsService;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
//@Component
//@Profile("!test")
public class ElectronicsDataBaseInitializer {
    private final ElectronicsService electronicsService;
    private List<ElectronicsResponseDTO> electronics;

    public ElectronicsDataBaseInitializer(ElectronicsService electronicsService) {
        this.electronicsService = electronicsService;

        initDatabase();
    }

    private List<ElectronicsResponseDTO> initDatabase() {

        var electronicsResponseDTO_one = ElectronicsResponseDTO.builder()
                .productType(ProductTypeEnum.ELECTRONICS)
                .title("Apple iPhone")
                .model("XX Red")
                .category(ElectronicCategoryEnum.SMARTPHONES)
                .characteristics("128GB, 5G")
                .description("Brand New, Official License")
                .quantity(100)
                .purchasePrice(BigDecimal.valueOf(1000))
                .build();

        var electronicsResponseDTO_two = ElectronicsResponseDTO.builder()
                .productType(ProductTypeEnum.ELECTRONICS)
                .title("Xiaomi Redmi")
                .model("XII Pro")
                .category(ElectronicCategoryEnum.SMARTPHONES)
                .characteristics("64Gb. 128Gb, 5G")
                .description("Brand New, Official License")
                .quantity(800)
                .purchasePrice(BigDecimal.valueOf(500))
                .build();

        var electronicsResponseDTO_three = ElectronicsResponseDTO.builder()
                .productType(ProductTypeEnum.ELECTRONICS)
                .title("Samsung Galaxy")
                .model("X Pro")
                .category(ElectronicCategoryEnum.SMARTPHONES)
                .characteristics("64Gb. 128Gb, 5G")
                .description("Renewed, Discount")
                .quantity(20)
                .purchasePrice(BigDecimal.valueOf(300))
                .build();

        var electronicsResponseDTO_four = ElectronicsResponseDTO.builder()
                .productType(ProductTypeEnum.ELECTRONICS)
                .title("Sony Trinitron OLED")
                .model("61\" OLED Smart-TV")
                .category(ElectronicCategoryEnum.TVS)
                .characteristics("Colorful, 8K, Dolby Atmos")
                .description("Brand New, Official Warrantyt")
                .quantity(10)
                .purchasePrice(BigDecimal.valueOf(1300))
                .build();

        var electronicsResponseDTO_five = ElectronicsResponseDTO.builder()
                .productType(ProductTypeEnum.ELECTRONICS)
                .title("Apple MacBook")
                .model("13 inch M2")
                .category(ElectronicCategoryEnum.LAPTOPS)
                .characteristics("M2, 13 inch, 128GB RAM")
                .description("Brand New, Official Warranty")
                .quantity(10)
                .purchasePrice(BigDecimal.valueOf(1300))
                .build();

        return List.of(electronicsResponseDTO_one,
                electronicsResponseDTO_two,
                electronicsResponseDTO_three,
                electronicsResponseDTO_four,
                electronicsResponseDTO_five);
    }

    public void init() {
        electronicsService.saveAll(electronics);
    }
}
