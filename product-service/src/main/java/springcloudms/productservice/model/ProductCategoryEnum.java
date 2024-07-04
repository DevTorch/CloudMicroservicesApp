package springcloudms.productservice.model;

import lombok.Getter;

@Getter
public enum ProductCategoryEnum {
    ELECTRONICS("Electronics"),
    BOOKS("Books"),
    SHOES("Shoes");

    private final String name;

    ProductCategoryEnum(String name) {
        this.name = name;
    }

}
