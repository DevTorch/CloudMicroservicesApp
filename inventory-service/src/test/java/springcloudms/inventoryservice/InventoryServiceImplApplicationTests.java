package springcloudms.inventoryservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import springcloudms.inventoryservice.model.BookProduct;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceImplApplicationTests {

//    @ServiceConnection
//    public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest");
//
//    @LocalServerPort
//    private Integer port;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @BeforeEach
//    void setup() {
//        RestAssured.baseURI = "http://localhost:";
//        RestAssured.port = port;
//    }
//
//    static {
//        postgresContainer.start();
//    }
//
//    @Test
//    void shouldReadInventoryTrue() {
//
//        var positiveResponse = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get(String.format("%s%s/api/inventory/inner/exists?skuCode=MacBook M4&quantity=1", RestAssured.baseURI, port))
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract().response().as(Boolean.class);
//        assertTrue(positiveResponse);
//    }
//
//    @Test
//    void shouldReadInventoryFalse() {
//
//        var negativeResponse = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get(String.format("%s%s/api/inventory/inner/exists?skuCode=iPad Air 2&quantity=1001", RestAssured.baseURI, port))
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract().response().as(Boolean.class);
//        assertFalse(negativeResponse);
//    }


}
