package springcloudms.customerservice.service;

import springcloudms.customerservice.dto.CustomerRequestSignUpDTO;
import springcloudms.customerservice.dto.CustomerResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerResponseDTO> getAllCustomers();
    Optional<CustomerResponseDTO> findCustomerDTOByUserId(Long customerId);
    void createNewCustomer(CustomerRequestSignUpDTO signUpDTO);
    void deleteCustomer(Long accountId);
}
