package springcloudms.productservice.model;

import com.mongodb.lang.NonNull;
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
    @NonNull
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

    public Book() {
        this.category = ProductCategoryEnum.BOOKS;
    }
}
