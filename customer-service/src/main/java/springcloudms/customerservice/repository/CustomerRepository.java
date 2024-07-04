package springcloudms.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springcloudms.customerservice.dto.CustomerResponseDTO;
import springcloudms.customerservice.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = """
            SELECT new springcloudms.customerservice.dto.CustomerResponseDTO(
            c.id,
            c.fullName,
            c.nickname,
            c.persistDateTime)
            FROM Customer c
            """)
    List<CustomerResponseDTO> getAllCustomers();

    @Query(value = "SELECT c FROM Customer c")
    List<Customer> findAllCustomers();

    @Query(value = """
            SELECT new springcloudms.customerservice.dto.CustomerResponseDTO(
            customer.id,
            customer.fullName,
            customer.nickname,
            customer.persistDateTime)
            FROM Customer customer
            WHERE customer.id = :customerId
            """)
    Optional<CustomerResponseDTO> getCustomerDTOByUserId(@Param(value = "customerId") Long customerId);

    @Query(value = """
            SELECT c.accountId
            FROM Customer c
            WHERE c.id = :customerId
            """)
    Long getAccountIdByUserId(@Param("customerId") Long customerId);

    @Modifying
    @Query(value = """
        DELETE FROM Customer c
        WHERE c.accountId = :accountId
        """)
    void deleteCustomerByAccountId(Long accountId);
}
