package springcloudms.authservice.service;

import springcloudms.authservice.dto.account.request.AccountLoginRequestDTO;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Boolean isAccountExists(Long accountId);
    Optional<AccountResponseDTO> findAccountById(Long accountId);
    List<AccountResponseDTO> getAllAccounts();
    Optional<AccountResponseDTO> findAccountByCredentials(AccountLoginRequestDTO loginRequestDTO);
    void createNewAccount(AccountSignUpDTO accountSignUpDTO);
    Optional<Account> findAccountByEmail(String email);
    Optional<Long> findAccountIdByEmail(String email);
}
