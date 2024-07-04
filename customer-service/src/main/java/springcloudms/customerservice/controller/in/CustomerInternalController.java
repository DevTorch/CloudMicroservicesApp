package springcloudms.customerservice.controller.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.customerservice.dto.CustomerRequestSignUpDTO;
import springcloudms.customerservice.dto.CustomerRequestUpdateDTO;
import springcloudms.customerservice.service.CustomerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/internal/customer")
public class CustomerInternalController {

    private final CustomerService customerService;

    @PatchMapping("/update")
    public ResponseEntity<HttpStatus> updateCustomer(@RequestBody CustomerRequestUpdateDTO updateDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createCustomer(@RequestBody CustomerRequestSignUpDTO signUpDTO) {
        customerService.createNewCustomer(signUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long accountId) {
        customerService.deleteCustomer(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
