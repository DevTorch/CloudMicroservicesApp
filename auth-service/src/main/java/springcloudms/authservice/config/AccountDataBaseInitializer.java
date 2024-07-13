package springcloudms.authservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.service.AccountService;

import java.time.LocalDateTime;

@Component
public class AccountDataBaseInitializer {

    private final AccountService accountService;


    public AccountDataBaseInitializer(AccountService accountService) {
        this.accountService = accountService;
    }

    AccountSignUpDTO accOne = AccountSignUpDTO.builder()
            .email("ADMIN@example.com")
            .password("12345")
            .fullName("Admin S Admin")
            .nickname("General")
            .about("Admin Account Description")
            .city("Moscow")
            .persistDateTime(LocalDateTime.now())
            .build();

    AccountSignUpDTO accTwo = AccountSignUpDTO.builder()
            .email("USER@example.com")
            .password("12345")
            .fullName("User S User")
            .nickname("User")
            .about("User Account Description")
            .city("St. Petersburg")
            .persistDateTime(LocalDateTime.now())
            .build();

    @PostConstruct
    public void init() {
        accountService.createNewAccount(accOne);
        accountService.createNewAccount(accTwo);
    }
}
