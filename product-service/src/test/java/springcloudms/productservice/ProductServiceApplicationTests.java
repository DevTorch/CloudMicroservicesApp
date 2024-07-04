package springcloudms.productservice;
;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import springcloudms.productservice.dto.ProductRequestDTO;
import springcloudms.productservice.dto.ProductResponseDTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }

    @Test
    void shouldCreateProductClassicMethod() {
        ProductRequestDTO requestBody = ProductRequestDTO.builder()
                .name("Xiaomi 12 Pro")
                .description("Smartphone Xiaomi")
                .price(BigDecimal.valueOf(100))
                .build();

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(String.format("%s%s/api/products/create", RestAssured.baseURI, port))
                .then()
                .statusCode(201)
                .extract();

        ProductResponseDTO responseDTO = response.as(ProductResponseDTO.class);

        assertNotNull(responseDTO.id());
        assertEquals("Xiaomi 12 Pro", responseDTO.name());
        assertEquals("Smartphone Xiaomi", responseDTO.description());
    }

    @Test
    void shouldCreateProduct() {
        String requestBody = """
                {
                	"name": "Redmi Book 15",
                    "description": "Xiaomi Redmi Notebook",
                    "price": 2500
                }
                """;
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(String.format("%s%s/api/products/create", RestAssured.baseURI, port))
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Redmi Book 15"))
                .body("description", Matchers.equalTo("Xiaomi Redmi Notebook"));
    }

}
