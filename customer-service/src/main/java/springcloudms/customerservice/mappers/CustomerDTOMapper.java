package springcloudms.customerservice.mappers;

import org.springframework.stereotype.Component;
import springcloudms.customerservice.dto.CustomerRequestSignUpDTO;
import springcloudms.customerservice.dto.CustomerResponseDTO;
import springcloudms.customerservice.model.Customer;

@Component
public class CustomerDTOMapper {

    public CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFullName(),
                customer.getNickname(),
                customer.getPersistDateTime()
        );
    }

    public Customer signUpDTOtoCustomer(CustomerRequestSignUpDTO signUpDTO) {
        var customer = new Customer();
        customer.setAccountId(signUpDTO.accountId());
        customer.setFullName(signUpDTO.fullName());
        customer.setNickname(signUpDTO.nickname());
        customer.setPersistDateTime(signUpDTO.persistDateTime());
        return customer;
    }
}
