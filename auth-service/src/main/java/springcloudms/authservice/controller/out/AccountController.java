package springcloudms.authservice.controller.out;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.authservice.dto.account.request.AccountLoginRequestDTO;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.service.AccountService;
import springcloudms.authservice.service.impl.AccountServiceImpl;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AccountController {

    private final AccountService accountService;

    @RequestMapping("/login")
    public ResponseEntity<AccountResponseDTO> login(@RequestBody AccountLoginRequestDTO loginRequestDTO) {

        if (accountService.findAccountByCredentials(loginRequestDTO).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(accountService.findAccountByCredentials(loginRequestDTO).get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("accountId") Long accountId) {
        return accountService.findAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody AccountSignUpDTO accountSignUpDTO) throws ExecutionException, InterruptedException {
        accountService.createNewAccount(accountSignUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
