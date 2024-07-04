package springcloudms.customerservice.controller.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.customerservice.dto.CustomerResponseDTO;
import springcloudms.customerservice.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerDTOController {

    private final CustomerService customerService;

    @Schema(description = "Get all customers")
    @GetMapping
    public List<CustomerResponseDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    @Tag(name = "Customer API", description = "Api для работы с пользовательскими данными.")
    @Operation(summary = "Get customer by customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    public ResponseEntity<CustomerResponseDTO> getCustomerByUserId(@PathVariable(value = "customerId") Long customerId) {
        Optional<CustomerResponseDTO> customer = customerService.findCustomerDTOByUserId(customerId);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
