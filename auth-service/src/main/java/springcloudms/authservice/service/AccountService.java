package springcloudms.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.authservice.dto.account.request.AccountLoginRequestDTO;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.dto.customer.CustomerCreateRequestDTO;
import springcloudms.authservice.model.Account;
import springcloudms.authservice.model.RoleNameEnum;
import springcloudms.authservice.repository.AccountRepository;
import springcloudms.authservice.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCrypt;
//    private final CustomerServiceFeignClient customerService;

    @Transactional(readOnly = true)
    public Boolean isAccountExists(Long accountId) {
        return accountRepository.findById(accountId).isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public Optional<AccountResponseDTO> getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .roles(account.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                        .isActive(account.getActive())
                        .build());
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .roles(account.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                        .isActive(account.getActive())
                        .build())
                .toList();
    }

    public Optional<AccountResponseDTO> findAccountByCredentials(AccountLoginRequestDTO loginRequestDTO) {

        //TODO: Переделать: сначала проверяем почту, потом по паролю (а далее encoder.match)

        return accountRepository.findByEmailAndPassword(loginRequestDTO.email(), loginRequestDTO.password())
                .map(account -> {
                    Set<RoleNameEnum> roles = account.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet());
                    return AccountResponseDTO.builder()
                            .id(account.getId())
                            .email(account.getEmail())
                            .roles(roles)
                            .isActive(account.getActive())
                            .build();
                });
    }

    @Transactional
    public void createNewAccount(AccountSignUpDTO accountSignUpDTO) {

        Account account = new Account();
        account.setEmail(accountSignUpDTO.email());
        account.setPassword(bCrypt.encode(accountSignUpDTO.password()));
        account.setActive(Boolean.TRUE);
        accountRepository.save(account);

        Long accountId = accountRepository.findByEmail(accountSignUpDTO.email())
                .map(Account::getId)
                .orElseThrow();

        //TODO Check fields
        //TODO Kafka Producer Send
        CustomerCreateRequestDTO customerCreateRequestDTO = CustomerCreateRequestDTO.builder()
                .accountId(accountId)
                .fullName(accountSignUpDTO.fullName())
                .nickname(accountSignUpDTO.nickname())
                .persistDateTime(accountSignUpDTO.persistDateTime())
                .build();

//        customerService.createCustomer(customerCreateRequestDTO);

    }
}
