package springcloudms.authservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.authservice.dto.account.request.AccountLoginRequestDTO;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.dto.customer.CustomerCreateRequestEvent;
import springcloudms.authservice.dto.order.OrderAccountCreateRequestEvent;
import springcloudms.authservice.feign.CustomerServiceFeignClient;
import springcloudms.authservice.model.Account;
import springcloudms.authservice.model.RoleNameEnum;
import springcloudms.authservice.repository.AccountRepository;
import springcloudms.authservice.repository.RoleRepository;
import springcloudms.authservice.service.AccountService;
import springcloudms.authservice.exception.KafkaSenderException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCrypt;
    private final RoleRepository roleRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional(readOnly = true)
    @Override
    public Boolean isAccountExists(Long accountId) {
        return accountRepository.findById(accountId).isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AccountResponseDTO> findAccountById(Long accountId) {

        log.info("findAccountById: {}", accountRepository.findById(accountId).toString());

        return accountRepository.findById(accountId)
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .roles(account.getRoles())
                        .isActive(account.getActive())
                        .build());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .roles(account.getRoles())
                        .isActive(account.getActive())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AccountResponseDTO> findAccountByCredentials(AccountLoginRequestDTO loginRequestDTO) {

        if (accountRepository.findByEmail(loginRequestDTO.email()).isEmpty()) {
            return Optional.empty();
        }

        //TODO: Переделать: сначала проверяем почту, потом по паролю (а далее encoder.match)

        return accountRepository.findByEmailAndPassword(loginRequestDTO.email(), loginRequestDTO.password())
                .map(account -> {
                    return AccountResponseDTO.builder()
                            .id(account.getId())
                            .email(account.getEmail())
                            .roles(account.getRoles())
                            .isActive(account.getActive())
                            .build();
                });
    }

    @Transactional
    @Override
    public void createNewAccount(AccountSignUpDTO accountSignUpDTO) {

        Account account = new Account();
        account.setEmail(accountSignUpDTO.email());
        account.setPassword(bCrypt.encode(accountSignUpDTO.password()));
        account.setRoles(Collections.singleton(roleRepository.findRoleByName(RoleNameEnum.valueOf("USER"))));
        account.setActive(Boolean.TRUE);
        var savedAccount = accountRepository.save(account);

        var savedAccountId = savedAccount.getId();

        log.info("Account: {}", savedAccount);

        //TODO Check fields
        //TODO Kafka Producer Send
        CustomerCreateRequestEvent customerCreateEvent = CustomerCreateRequestEvent.builder()
                .accountId(savedAccount.getId())
                .fullName(accountSignUpDTO.fullName())
                .nickname(accountSignUpDTO.nickname())
                .persistDateTime(accountSignUpDTO.persistDateTime() == null
                        ? LocalDateTime.now()
                        : accountSignUpDTO.persistDateTime())
                .build();

        String signUpTopic = "customer-signup-event-topic";

        ProducerRecord<String, Object> newCustomerRecord = new ProducerRecord<>(
                signUpTopic,
                String.valueOf(customerCreateEvent.accountId()),
                customerCreateEvent);

        newCustomerRecord.headers()
                .add("messageId", UUID.randomUUID().toString().getBytes())
                .add("messageKey", customerCreateEvent.accountId().toString().getBytes())
                .add("eventType", "NewCustomerCreatedEvent".getBytes());

        //TODO Kafka Transaction #1

        try {

            SendResult<String, Object> sendResult = kafkaTemplate.send(newCustomerRecord).get();

            log.info("SendResult {} ", sendResult.getRecordMetadata());
            log.info("SendResult {} ", new ObjectMapper().writeValueAsString(sendResult.getRecordMetadata()));

        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {

            throw new KafkaSenderException(String.format("Failed to send message to topic %s", signUpTopic), e);
        }

        OrderAccountCreateRequestEvent orderRequestEvent = OrderAccountCreateRequestEvent.builder()
                .accountId(savedAccountId)
                .createdTimeStamp(LocalDateTime.now())
                .build();

        String shoppingCartTopic = "customer-shopping-cart-event-topic";
        ProducerRecord<String, Object> newOrderRecord = new ProducerRecord<>(
                shoppingCartTopic,
                String.valueOf(orderRequestEvent.accountId()),
                orderRequestEvent);

        newOrderRecord.headers()
                .add("messageId", UUID.randomUUID().toString().getBytes())
                .add("timestamp", LocalDateTime.now().toString().getBytes())
                .add("eventType", "NewOrderAccountCreatedEvent".getBytes());

        //TODO Kafka Transaction #2

        try {

            SendResult<String, Object> sendResult = kafkaTemplate.send(newOrderRecord).get();

            log.info("Transaction 2 Metadata {} ", sendResult.getRecordMetadata());
            log.info("Transaction 2 Content {} ", new ObjectMapper().writeValueAsString(sendResult.getRecordMetadata()));

        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {

            throw new KafkaSenderException(String.format("Failed to send message to topic %s", signUpTopic), e);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Long> findAccountIdByEmail(String email) {
        return Optional.ofNullable(accountRepository.findAccountIdByEmail(email));
    }
}
