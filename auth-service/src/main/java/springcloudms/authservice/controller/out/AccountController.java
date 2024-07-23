package springcloudms.authservice.controller.out;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.authservice.dto.account.request.AccountLoginRequestDTO;
import springcloudms.authservice.dto.account.request.AccountSignUpDTO;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.exception.AccountNotFoundException;
import springcloudms.authservice.exception.KafkaSenderException;
import springcloudms.authservice.service.AccountService;
import springcloudms.authservice.service.impl.AccountServiceImpl;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    @RequestMapping("/login")
    public ResponseEntity<AccountResponseDTO> login(@RequestBody @Valid AccountLoginRequestDTO loginRequestDTO) {

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
    //TODO протестировать Advice
    @GetMapping("/account/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        return accountService.findAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid AccountSignUpDTO accountSignUpDTO) {
        log.info("signUp: {}", accountSignUpDTO.toString());
        try {
            accountService.createNewAccount(accountSignUpDTO);
            return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
        } catch (KafkaSenderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
