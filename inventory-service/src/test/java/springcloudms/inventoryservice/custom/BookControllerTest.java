package springcloudms.inventoryservice.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springcloudms.inventoryservice.model.dto.BookResponseDTO;
import springcloudms.inventoryservice.model.enums.ProductTypeEnum;
import springcloudms.inventoryservice.model.enums.WarehousesEnum;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
//@MockBeans({
//        @MockBean(AuthServiceFeignClient.class),
//        @MockBean(ProfileServiceFeignClient.class)
//})
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest extends CustomizedContextMainTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;


    @Test
    public void shouldAddNewBookFromBookDTO() throws Exception {

        BookResponseDTO bookResponseDTO = new BookResponseDTO(
                UUID.randomUUID().toString(),
                ProductTypeEnum.BOOKS,
                "Harry Potter and the Philosopher's Stone",
                "J.K. Rowling",
                "Houghton Mifflin Harcourt",
                "978-1-56619-909-9",
                WarehousesEnum.WAREHOUSE_SEATTLE,
                55,
                new BigDecimal("10.00")
        );

        mockMvc.perform(post("/api/books/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookResponseDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is(bookResponseDTO.title())));
    }

}
