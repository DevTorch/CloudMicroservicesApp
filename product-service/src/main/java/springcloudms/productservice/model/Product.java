package springcloudms.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collation = "products", value = "products")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @Field("product_id")
    private Long id;
    @Field("product_category")
    private ProductCategoryEnum category;
    @Field("product_name")
    private String title;
    @Field("product_description")
    private String description;
    @Field("price")
    private BigDecimal price;

}
