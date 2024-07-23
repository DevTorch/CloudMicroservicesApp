package springcloudms.authservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.authservice.dto.account.request.AccountLoginRequestDTO;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.dto.customer.CustomerCreateRequestEvent;
import springcloudms.authservice.dto.order.OrderAccountCreateRequestEvent;
import springcloudms.authservice.exception.AccountNotFoundException;
import springcloudms.authservice.exception.KafkaSenderException;
import springcloudms.authservice.model.Account;
import springcloudms.authservice.model.RoleNameEnum;
import springcloudms.authservice.repository.AccountRepository;
import springcloudms.authservice.repository.RoleRepository;
import springcloudms.authservice.service.AccountService;
import springcloudms.core.constants.CoreConstants;

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

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Boolean isAccountExists(Long accountId) {
        return accountRepository.findById(accountId).isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Optional<AccountResponseDTO> findAccountById(Long accountId) throws AccountNotFoundException {

        log.info("findAccountById: {}", accountRepository.findById(accountId));

        Optional<AccountResponseDTO> accountResponseDTO = accountRepository.findById(accountId)
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .roles(account.getRoles())
                        .isActive(account.getActive())
                        .build());

        if (accountResponseDTO.isPresent()) {
            return accountResponseDTO;
        } else {
            throw new AccountNotFoundException(String.format("Account not found %s: ", accountId));
        }
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
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

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Optional<AccountResponseDTO> findAccountByCredentials(AccountLoginRequestDTO loginRequestDTO) {

        if (accountRepository.findByEmail(loginRequestDTO.email()).isEmpty()) {
            return Optional.empty();
        }

        //TODO: Переделать: сначала проверяем почту, потом по паролю (а далее encoder.match)

        return accountRepository.findByEmailAndPassword(loginRequestDTO.email(), loginRequestDTO.password())
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .roles(account.getRoles())
                        .isActive(account.getActive())
                        .build());
    }

    @Override
//    @Transactional(value = "transactionManager", //НЕ ЗАБЫВАЙ о том, как роллбэчить транзакции
//            rollbackFor = {ConnectException.class, ExecutionException.class, KafkaSenderException.class},
//            noRollbackFor = {NullPointerException.class})
    @Transactional("transactionManager")
    public void createNewAccount(AccountSignUpDTO accountSignUpDTO) {

        Account account = new Account();
        account.setEmail(accountSignUpDTO.email());
        account.setPassword(bCrypt.encode(accountSignUpDTO.password()));
        account.setRoles(Collections.singleton(roleRepository.findRoleByName(RoleNameEnum.valueOf("USER"))));
        account.setActive(Boolean.TRUE);

        try {

            var savedAccount = accountRepository.save(account);

            CustomerCreateRequestEvent customerCreateEvent = CustomerCreateRequestEvent.builder()
                    .accountId(savedAccount.getId())
                    .fullName(accountSignUpDTO.fullName())
                    .nickname(accountSignUpDTO.nickname())
                    .persistDateTime(accountSignUpDTO.persistDateTime() == null
                            ? LocalDateTime.now()
                            : accountSignUpDTO.persistDateTime())
                    .build();

            ProducerRecord<String, Object> newCustomerRecord = new ProducerRecord<>(
                    CoreConstants.CUSTOMER_SIGNUP_EVENTS_TOPIC,
                    String.valueOf(customerCreateEvent.accountId()),
                    customerCreateEvent);

            newCustomerRecord.headers()
                    .add("messageId", UUID.randomUUID().toString().getBytes())
                    .add("messageKey", customerCreateEvent.accountId().toString().getBytes())
                    .add("eventType", "NewCustomerCreatedEvent".getBytes());

            OrderAccountCreateRequestEvent orderRequestEvent = OrderAccountCreateRequestEvent.builder()
                    .accountId(savedAccount.getId())
                    .createdTimeStamp(LocalDateTime.now())
                    .build();

            String accountOrderTopic = CoreConstants.ACCOUNT_ORDER_EVENTS_TOPIC;
            ProducerRecord<String, Object> newOrderRecord = new ProducerRecord<>(
                    accountOrderTopic,
                    String.valueOf(orderRequestEvent.accountId()),
                    orderRequestEvent);

            newOrderRecord.headers()
                    .add("messageId", UUID.randomUUID().toString().getBytes())
                    .add("timestamp", LocalDateTime.now().toString().getBytes())
                    .add("eventType", "NewOrderAccountCreatedEvent".getBytes());


            //TODO Kafka Transaction #1
            var sendResult = kafkaTemplate.send(newCustomerRecord).get();
            log.info("SendResult First Transaction {} ", sendResult.getRecordMetadata());

            //TODO Kafka Transaction #2
            var accountOrderSendResult = kafkaTemplate.send(newOrderRecord).get();
            log.info("SendResult Second Transaction {} ", accountOrderSendResult.getRecordMetadata());

        } catch (InterruptedException | ExecutionException e) {
            throw new KafkaSenderException(String.format("Failed to send message %s", e));
        }
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Optional<Long> findAccountIdByEmail(String email) {
        return Optional.ofNullable(accountRepository.findAccountIdByEmail(email));
    }
}