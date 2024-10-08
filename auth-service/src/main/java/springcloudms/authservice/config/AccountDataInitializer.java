package springcloudms.authservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AccountDataInitializer {

    private final AccountService accountService;

    public AccountDataInitializer(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    public void init() {
        AccountSignUpDTO accOne = AccountSignUpDTO.builder()
                .email("ADMIN@example.com")
                .password("12345")
                .fullName("Administrator")
                .nickname("Admin")
                .about("Administrator Account Description")
                .city("New York")
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

        AccountSignUpDTO accThree = AccountSignUpDTO.builder()
                .email("JohnDoe@example.com")
                .password("12345")
                .fullName("John Doe")
                .nickname("JohnnyD")
                .about("John Doe Account Description")
                .city("Washington DC")
                .persistDateTime(LocalDateTime.now())
                .build();

        List<AccountSignUpDTO> accounts = List.of(accOne, accTwo, accThree);

//        accountService.createNewAccount(accOne);
//        accountService.createNewAccount(accTwo);
//        accountService.createNewAccount(accThree);

    }
}

