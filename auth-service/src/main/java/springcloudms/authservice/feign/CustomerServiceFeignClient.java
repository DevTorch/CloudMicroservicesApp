package springcloudms.authservice.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springcloudms.authservice.dto.customer.CustomerCreateRequestDTO;
import springcloudms.authservice.dto.customer.CustomerUpdateRequestDTO;
import springcloudms.authservice.exception.CustomerServiceResponseException;

@FeignClient(name = "customer-service", path = "/api/internal/customer",
        fallbackFactory = CustomerServiceFeignClient.CustomerFallbackFactory.class
)
public interface CustomerServiceFeignClient {

    @PatchMapping("/update")
    ResponseEntity<HttpStatus> updateCustomer(@RequestBody CustomerUpdateRequestDTO updateDTO);

    @PostMapping("/create")
    ResponseEntity<HttpStatus> createCustomer(@RequestBody CustomerCreateRequestDTO signUpDTO);

    @DeleteMapping("/delete/{accountId}")
    ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long accountId);

    public class CustomerFallbackFactory implements FallbackFactory<CustomerFallbackFactory.FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }

        record FallbackWithFactory(String reason) implements CustomerServiceFeignClient {
            @Override
            public ResponseEntity<HttpStatus> updateCustomer(CustomerUpdateRequestDTO updateDTO) {
                final String reason = "Customer with id %s not found".formatted(updateDTO.accountId());
                throw new CustomerServiceResponseException(reason);
            }

            @Override
            public ResponseEntity<HttpStatus> createCustomer(CustomerCreateRequestDTO signUpDTO) {
                final String reason = """
                        An error occurred during the creation of new customer with accountId %s
                        """.formatted(signUpDTO.accountId());
                throw new CustomerServiceResponseException(reason);
            }

            @Override
            public ResponseEntity<HttpStatus> deleteCustomer(Long accountId) {
                final String reason = """
                        An error occurred during the deletion of customer with accountId %s
                        """.formatted(accountId);
                throw new CustomerServiceResponseException(reason);
            }
        }
    }
}
