package springcloudms.orderservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import springcloudms.orderservice.stubs.InventoryClientStub;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderCompleteServiceApplicationTests {

    @ServiceConnection
    public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:";
        RestAssured.port = port;
    }

    static {
        postgresContainer.start();
    }

    @Test
    @Transactional
    void shouldSubmitOrder() {
        String submitOrderBody = """
                {
                	"skuCode": "MacBook M4",
                    "price": 5100.0,
                    "quantity": 1
                }
                """;

        /* WARN: Пробелы в строках не матчатся напрямую, только через %20 */

        InventoryClientStub.stubClientCall("MacBook%20M4", 1);

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(submitOrderBody)
                .when()
                .post(String.format("%s%s/api/orders/submit", RestAssured.baseURI, port))
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().asString();

        assertThat(response, Matchers.is("Order placed successfully"));
    }

}
