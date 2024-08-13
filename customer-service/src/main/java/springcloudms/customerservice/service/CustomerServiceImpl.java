package springcloudms.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.customerservice.dto.CustomerRequestSignUpDTO;
import springcloudms.customerservice.dto.CustomerResponseDTO;
import springcloudms.customerservice.feign.AuthenticationServiceFeignClient;
import springcloudms.customerservice.mappers.CustomerDTOMapper;
import springcloudms.customerservice.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthenticationServiceFeignClient authFeignClient;
    private final CustomerDTOMapper customerDTOMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAllCustomers()
                .stream()
                .filter(customer -> authFeignClient.isAccountExists(customer.getAccountId()))
                .map(customer -> CustomerResponseDTO.builder()
                        .id(customer.getId())
                        .fullName(customer.getFullName())
                        .nickname(customer.getNickname())
                        .email(authFeignClient.getAccountById(customer.getAccountId()).getBody().email())
                        .registrationDate(customer.getPersistDateTime())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CustomerResponseDTO> findCustomerDTOByUserId(Long customerId) {

        return customerRepository.findById(customerId)
                .filter(customer -> authFeignClient.isAccountExists(customer.getAccountId()))
                .map(customer -> CustomerResponseDTO.builder()
                        .id(customer.getId())
                        .fullName(customer.getFullName())
                        .nickname(customer.getNickname())
                        .email(authFeignClient.getAccountById(customer.getAccountId()).getBody().email())
                        .registrationDate(customer.getPersistDateTime())
                        .build());
    }
    @Transactional
    @Override
    public void createNewCustomer(CustomerRequestSignUpDTO signUpDTO) {
        customerRepository.save(customerDTOMapper.signUpDTOtoCustomer(signUpDTO));
    }
    @Transactional
    @Override
    public void deleteCustomer(Long accountId) {
        customerRepository.deleteCustomerByAccountId(accountId);
    }
}
