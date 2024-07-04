package springcloudms.inventoryservice.custom;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityManager;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import springcloudms.inventoryservice.InventoryServiceApplication;
import springcloudms.inventoryservice.model.BookProduct;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, SqlScriptsTestExecutionListener.class})
@SpringBootTest(classes = InventoryServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = CustomizedContextMainTest.Initializer.class)
public class CustomizedContextMainTest {

    private static final Logger log = LoggerFactory.getLogger(CustomizedContextMainTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private Integer port;

    @Container
    @ClassRule
    @ServiceConnection
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("inventory-service-test")
            .withUsername("postgres")
            .withPassword("root")
            .withReuse(true)
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(
                            new PortBinding(Ports.Binding.bindPort(54330), new ExposedPort(5432)))));

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/sql/BookControllerBeforeTest.sql")
    void name() throws Exception {
        log.info("DataBase connected: {}", postgres.getJdbcUrl());

        Long productId = 1001L;
        String name = "BOOK_ONE";
        String author = "AUTHOR_ONE";
        Integer quantity = 10;

//        entityManager
//                .persist(new BookProduct(productId, name, author, quantity));

        mockMvc.perform(get("/api/books/{productId}", productId))
                .andExpect(status().isOk());

        assertTrue(true);
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword(),
                    "spring.datasource.driver-class-name=" + postgres.getDriverClassName()
            ).applyTo(configurableApplicationContext.getEnvironment());

            var environment = configurableApplicationContext.getEnvironment();
            environment.addActiveProfile("test");
        }

    }
}
