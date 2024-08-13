package springcloudms.productservice.model;

import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collation = "products", value = "books")
@Getter
@Setter
@ToString
@AllArgsConstructor
//@NoArgsConstructor
public class Book {

    @Id
    @NonNull
    @Field("article_no")
    private String articleNo;
    @Field("category")
    private ProductCategoryEnum category;
    @NonNull
    @Field("ISBN")
    private String isbnNo;
    @NotEmpty
    @Field("author")
    private String author;
    @NonNull
    @Field("title")
    private String title;
    @Field(value = "description")
    private String description;
    @NonNull
    @Field("price")
    private BigDecimal price;
    @Field("publisher")
    private String publisher;
    @Field("positive_feedback")
    private Integer positiveFeedback;
    @Field("negative_feedback")
    private Integer negativeFeedback;
    @Field("rating")
    private Double rating;
    @PositiveOrZero
    @Field("stock")
    private Integer stock;
    @Field("image")
    private String image;

    public Book() {
        this.category = ProductCategoryEnum.BOOKS;
    }
}
